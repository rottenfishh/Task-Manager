const API_URL = "http://localhost:8080/api/tasks"
let tasks = []
let categories = []
const csrfToken = document.querySelector('meta[name="_csrf"]').content;
const csrfHeader = document.querySelector('meta[name="_csrf_header"]').content;

function renderTasks() {
    const tasksList = document.getElementById("tasksList");
    tasksList.innerHTML = '';

    tasks.forEach((task) => {
        const li = document.createElement("li");
        li.classList.add("task");

        // --- Create header container for name + buttons ---
        const headerDiv = document.createElement("div");
        headerDiv.classList.add("task-header");
        headerDiv.style.display = "flex";
        headerDiv.style.justifyContent = "space-between";
        headerDiv.style.alignItems = "center";
        headerDiv.style.width = "100%";

        // --- Task name ---
        const textArea = document.createElement("textarea");
        textArea.value = task.taskName;
        textArea.onchange = () => updateTaskName(task.id, textArea.value);
        if (task.status === "FINISHED") textArea.classList.add("completed");
        textArea.style.flexGrow = "1";
        textArea.style.marginRight = "10px";
        adjustTextAreaHeight(textArea);
        textArea.addEventListener("input", () => adjustTextAreaHeight(textArea));

        // buttons container
        const buttonsDiv = document.createElement("div");
        buttonsDiv.style.display = "flex";
        buttonsDiv.style.gap = "5px";

        const showDescBtn = document.createElement("button");
        showDescBtn.textContent = "▼"; // down arrow
        showDescBtn.classList = "showDescBtn";

        showDescBtn.onclick = () => {
            if (descArea.style.display === "none") {
                descArea.style.display = "block";
                showDescBtn.textContent = "▲"; // arrow up when open
            } else {
                descArea.style.display = "none";
                showDescBtn.textContent = "▼"; // arrow down when closed
            }
        };


        const categoryDiv = document.createElement("div");
        const ul = document.createElement("ul");
        ul.id = `ulCategory-${task.id}`;
        categoryDiv.appendChild(ul);

        const categoryTextArea = document.createElement("textarea");
        categoryTextArea.onchange = () => updateCategory(task.id, categoryTextArea.value);
        categoryTextArea.style.flexGrow = "1";
        categoryTextArea.style.marginRight = "10px";
        adjustTextAreaHeight(categoryTextArea);
        categoryTextArea.addEventListener("input", () => adjustTextAreaHeight(categoryTextArea));

        categoryDiv.appendChild(categoryTextArea);
        categoryDiv.style.display = "none";

        const showCategoryBtn = document.createElement("button");
        showCategoryBtn.textContent = "+";
        showCategoryBtn.classList = "showCategoryBtn";
        showCategoryBtn.onclick = () => {
            if (categoryDiv.style.display === "none") {
                categoryDiv.style.display = "block";
                showCategoryBtn.textContent = "-"; // arrow up when open
            } else {
                categoryDiv.style.display = "none";
                showCategoryBtn.textContent = "+"; // arrow down when closed
            }
        }

        const deleteButton = document.createElement("button");
        deleteButton.type = "button";
        deleteButton.innerText = "Delete";
        deleteButton.onclick = () => deleteTask(task.id);

        buttonsDiv.appendChild(showDescBtn);
        buttonsDiv.appendChild(deleteButton);

        // --- Checkbox (optional) ---
        const checkbox = document.createElement("input");
        checkbox.type = "checkbox";
        checkbox.checked = task.status === "FINISHED";
        checkbox.onchange = () => {
            const newStatus = checkbox.checked ? "FINISHED" : "CREATED";
            updateStatus(task.id, newStatus);
        };

        buttonsDiv.appendChild(checkbox);

        // Append task name and buttons to headerDiv
        headerDiv.appendChild(textArea);
        headerDiv.appendChild(buttonsDiv);

        // --- Description area ---
        const descArea = document.createElement("textarea");
        descArea.value = task.taskDescription;
        descArea.style.display = "none"; // initially hidden
        descArea.onchange = () => updateTaskDescription(task.id, descArea.value);
        adjustTextAreaHeight(descArea);
        descArea.addEventListener("input", () => adjustTextAreaHeight(descArea));

        // Append header and description to li
        li.appendChild(headerDiv);
        li.appendChild(descArea);
        li.appendChild(categoryDiv);
        li.appendChild(showCategoryBtn);
        tasksList.appendChild(li);
    });
}


// Function to dynamically adjust the height of the textarea
function adjustTextAreaHeight(textarea) {
    textarea.style.height = 'auto'; // Reset the height
    textarea.style.height = (textarea.scrollHeight) + 'px'; // Set to scrollHeight for expansion
}


function loadTasks() {
    fetch(API_URL)
        .then(response => response.json())
        .then(listTasks => {
            console.log(listTasks);
            tasks = listTasks;
            tasks.sort((a,b) => a.id - b.id);
            renderTasks();
        })
        .catch(error => console.error("Error while loading tasks", error));
}

function loadCategories(id = "categoryList") {
    fetch("api/categories")
        .then(response => response.json())
        .then(listCategories => {
            console.log(listCategories);
            categories = listCategories;
            categories.sort((a,b) => a.id - b.id);
            renderCategories(id);
        })
}

function renderCategories(id = "categoryList") {
    categoriesList = document.getElementById(id);
    categories.forEach(category => {
        const li = document.createElement("li");
        li.classList.add("category");
        li.innerText(category.categoryName)
        categoriesList.appendChild(li);
    })
}


function createTask() { //reads from newTask id input
    let inputName = document.getElementById("newTaskName");
    let inputDesc = document.getElementById("newTaskDescription");
    let newJson = {
        "name" : inputName.value,
        "description": inputDesc.value
    }
    fetch(API_URL, {
        method: "POST",
        headers: {
            "Content-Type": "application/json",
            [csrfHeader]: csrfToken
        },

        body: JSON.stringify(newJson),
    })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(`Failed to add task: ${response.status} ${response.statusText} - ${text}`);
                });
            }
            return response.text();
        })
        .then(() => {
            inputName.value = "";
            inputDesc.value = "";
            loadTasks();
        })
        .catch(error => console.error(error));
}

//const taskText = document.getElementById('newTask').value;
//         if (!taskText) return;
function updateTaskName(id, newName){
    fetch(`${API_URL}/${id}`, {
        method: "PUT",
        headers: {"Content-type" : "plain/text", [csrfHeader]: csrfToken},
        body: newName,
    }).then(() => loadTasks())
        .catch(error => console.error("Error while updating task name", error));
}

function updateTaskDescription(id, newDescription){
    fetch(`${API_URL}/${id}/description`, {
        method: "PUT",
        headers: {"Content-type" : "plain/text", [csrfHeader]: csrfToken},
        body: newDescription,
    }).then(() => loadTasks())
        .catch(error => console.error("Error while updating task name", error));
}

function updateStatus(id, newStatus) {
    fetch(`${API_URL}/${id}/status`, {
        method: "PUT",
        headers: {"Content-type" : "plain/text", [csrfHeader]: csrfToken},
        body: newStatus,
    }).then(() => {
        const task = tasks.find(t => t.id === id);
        if (task) task.status = newStatus;
    })
        .catch(error => console.error("Error while updating task description", error));
}

function updateCategory(id, newCategory) {
    fetch(`${API_URL}/${id}/category`, {
        method: "PUT",
        headers: {"Content-type": "plain/text", [csrfHeader]: csrfToken},
        body: newCategory,
    }).then(() => loadTasks())
        .catch(error => console.error("Error while updating task name", error));
    }
function deleteTask(id) {
    fetch(`${API_URL}/${id}`, {
        method: "DELETE",
        headers: {[csrfHeader]: csrfToken}
    }).then(() => loadTasks())
        .catch(error => console.error("Error while deleting task", error));
}

function categoryFilter() {
    document.getElementById("myDropdown").classList.toggle("show");
}

function filterFunction() {
    const input = document.getElementById("myInput");
    const filter = input.value.toUpperCase();
    const div = document.getElementById("myDropdown");
    const a = div.getElementsByTagName("a");
    for (let i = 0; i < a.length; i++) {
        txtValue = a[i].textContent || a[i].innerText;
        if (txtValue.toUpperCase().indexOf(filter) > -1) {
            a[i].style.display = "";
        } else {
            a[i].style.display = "none";
        }
    }
}

document.addEventListener("DOMContentLoaded", loadTasks);
document.addEventListener("DOMContentLoaded", () => loadCategories("ulCategory"));