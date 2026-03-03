function login() {
    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    fetch("http://localhost:8081/auth/login", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ username, password })
    })
    .then(res => {
        if (!res.ok) throw new Error("Invalid login");
        return res.text();
    })
    .then(token => {
        localStorage.setItem("token", token);
        window.location.href = "dashboard.html";
    })
    .catch(err => {
        document.getElementById("error").innerText = "Login failed";
    });
}
