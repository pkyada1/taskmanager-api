
document.getElementById("addTaskButton").addEventListener("click", async function() {

    const title = document.getElementById("title").value.trim();
    const description = document.getElementById("description").value.trim();
    const dueDate = document.getElementById("dueDate").value;

    if (!title || !description || !dueDate) {
        alert("Please fill out all fields.");
        return;
    }

    const task = {
        title: title,
        description: description,
        dueDate: dueDate
    };

    try {
        const response = await fetch("/tasks", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(task)
        });

        if (!response.ok) {
            throw new Error("Failed to add task");
        }

        alert("Task added successfully!");

        document.getElementById("taskForm").reset();

        await loadTasks();


    } catch (error) {
        alert("Something went wrong: " + error.message);
    }

    async function loadTasks() {

        const taskList = document.getElementById("taskList");

        try {
            const response = await fetch("/tasks");

            if (!response.ok) {
            throw new Error("Failed to load tasks");
            }

            const tasks = await response.json();

            taskList.innerHTML = "";

            if (tasks.length === 0) {
            taskList.textContent = "No tasks yet!";
            return;
            }

            tasks.forEach(function(task) {

            const taskItem = document.createElement("div");

            const title = document.createElement("h3");
            title.textContent = task.title;

            const description = document.createElement("p");
            description.textContent = task.description;

            const dueDate = document.createElement("p");
            dueDate.textContent = "Due: " + task.dueDate;

            const status = document.createElement("p");
            status.textContent = task.completed
                ? "Completed"
                : "Not Completed";

            
            const completeButton = document.createElement("button");
            completeButton.textContent = "Complete";

            completeButton.disabled = task.completed;

            completeButton.addEventListener("click", function() {
                completeTask(task.id);
            });

            const deleteButton = document.createElement("button");
            deleteButton.textContent = "Delete";

            deleteButton.addEventListener("click", function() {
                deleteTask(task.id);
            });

            taskItem.append(
                title,
                description,
                dueDate,
                status,
                completeButton,
                deleteButton
            );


            taskList.appendChild(taskItem);
        });

        } catch (error) {
            taskList.textContent = "Error loading tasks: " + error.message;
        }
    }

    async function completeTask(id) {
        try {
            const response = await fetch(`/tasks/${id}/complete`, {
                method: "PATCH"
            });

        if (!response.ok) {
            throw new Error("Failed to complete task");
        }

        await loadTasks();

        } catch (error) {
            alert(error.message);
        }
    }

    async function deleteTask(id) {
        if (!confirm("Delete this task?")) {
            return;
        }

        try {
            const response = await fetch(`/tasks/${id}`, {
                method: "DELETE"
            });

        if (!response.ok) {
            throw new Error("Failed to delete task");
        }

        await loadTasks();

        } catch (error) {
            alert(error.message);
        }
    }

    loadTasks();



});
