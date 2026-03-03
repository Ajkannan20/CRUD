function logout() {
    localStorage.removeItem("token");
    window.location.href = "/Login.html";
}

function loadUsers() {
    fetch("/api/users", {
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(res => {
        if (res.status === 401) {
            alert("Session expired. Please login again.");
            logout();
            throw new Error("Unauthorized");
        }
        return res.json();
    })
    .then(users => {
        const body = document.getElementById("userTableBody");
        body.innerHTML = "";

        users.forEach(u => {
            body.innerHTML += `
                <tr>
                    <td>${u.name}</td>
                    <td>${u.email}</td>
                    <td>
                        <button onclick="editUser(${u.id}, '${u.name}', '${u.email}')">
                            Edit
                        </button>
                    </td>
                    <td>
                        <button onclick="deleteUser(${u.id})">
                            Delete
                        </button>
                    </td>
                </tr>
            `;
        });
    })
    .catch(() => {});
}

function saveUser() {
    const id = document.getElementById("userId").value;
    const name = document.getElementById("name").value.trim();
    const email = document.getElementById("email").value.trim();

    if (!name) {
        alert("Please enter name");
        return;
    }

    if (!email) {
        alert("Please enter email");
        return;
    }

    if (!/^[a-zA-Z ]+$/.test(name)) {
        alert("Name should contain only letters");
        return;
    }

    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        alert("Please enter valid email");
        return;
    }

    const method = id ? "PUT" : "POST";
    const url = id ? `/api/users/${id}` : "/api/users";

    fetch(url, {
        method: method,
        headers: {
            "Content-Type": "application/json",
            "Authorization": "Bearer " + localStorage.getItem("token")
        },
        body: JSON.stringify({ name, email })
    })
    .then(res => {
        if (res.status === 401) {
            alert("Session expired. Please login again.");
            logout();
            throw new Error();
        }
        resetForm();
        loadUsers();
    })
    .catch(() => {});
}

function editUser(id, name, email) {
    document.getElementById("userId").value = id;
    document.getElementById("name").value = name;
    document.getElementById("email").value = email;
    document.getElementById("saveBtn").innerText = "Update";
}

function deleteUser(id) {
    if (!confirm("Are you sure you want to delete this user?")) return;

    fetch(`/api/users/${id}`, {
        method: "DELETE",
        headers: {
            "Authorization": "Bearer " + localStorage.getItem("token")
        }
    })
    .then(res => {
        if (res.status === 401) {
            alert("Session expired. Please login again.");
            logout();
            throw new Error();
        }
        loadUsers();
    })
    .catch(() => {});
}

function resetForm() {
    document.getElementById("userId").value = "";
    document.getElementById("name").value = "";
    document.getElementById("email").value = "";
    document.getElementById("saveBtn").innerText = "Add";
}

loadUsers();
