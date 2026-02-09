const listEl = document.getElementById("student-list");
const formEl = document.getElementById("student-form");
const statusEl = document.getElementById("status");
const updateBtn = document.getElementById("update-btn");
const resetBtn = document.getElementById("reset-btn");
const refreshBtn = document.getElementById("refresh-btn");

const idInput = document.getElementById("student-id");
const nameInput = document.getElementById("name");
const emailInput = document.getElementById("email");
const ageInput = document.getElementById("age");

const BASE_URL = "http://localhost:8080";
const API = {
  list: `${BASE_URL}/students`,
  add: `${BASE_URL}/add-student`,
  update: (id) => `${BASE_URL}/update/${id}`,
  patch: (id) => `${BASE_URL}/patch/${id}`,
  delete: (id) => `${BASE_URL}/delete/${id}`,
};

const setStatus = (message, isError = false) => {
  statusEl.textContent = message;
  statusEl.style.color = isError ? "#b6372b" : "#2c8a7d";
};

const clearForm = () => {
  idInput.value = "";
  formEl.reset();
  updateBtn.disabled = true;
  setStatus("");
};

const getPayload = () => ({
  name: nameInput.value.trim(),
  email: emailInput.value.trim(),
  age: Number(ageInput.value),
});

const getPatchPayload = () => {
  const payload = {};
  const name = nameInput.value.trim();
  const email = emailInput.value.trim();
  const ageValue = ageInput.value.trim();

  if (name) payload.name = name;
  if (email) payload.email = email;
  if (ageValue) {
    const age = Number(ageValue);
    if (!Number.isNaN(age)) payload.age = age;
  }

  return payload;
};

const fetchStudents = async () => {
  try {
    const res = await fetch(API.list);
    if (!res.ok) throw new Error("Unable to load students.");
    const data = await res.json();
    renderStudents(data);
  } catch (err) {
    renderError(err.message);
  }
};

const renderError = (message) => {
  listEl.innerHTML = `<div class="empty">${message}</div>`;
};

const renderStudents = (students) => {
  if (!students.length) {
    listEl.innerHTML = `<div class="empty">No students yet. Add the first one above.</div>`;
    return;
  }

  listEl.innerHTML = students
    .map(
      (student) => `
      <article class="card">
        <span class="pill">ID: ${student.id}</span>
        <h3>${student.name}</h3>
        <div class="card__meta">${student.email}</div>
        <div class="card__meta">Age: ${student.age}</div>
        <div class="card__actions">
          <button class="btn btn--ghost" data-action="edit" data-id="${student.id}">Edit</button>
          <button class="btn btn--primary" data-action="delete" data-id="${student.id}">Delete</button>
        </div>
      </article>
    `
    )
    .join("");
};

const addStudent = async (payload) => {
  try {
    const res = await fetch(API.add, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    if (!res.ok) throw new Error("Add failed. Check input values.");
    await fetchStudents();
    clearForm();
    setStatus("Student added.");
  } catch (err) {
    setStatus(err.message, true);
  }
};

const updateStudent = async (id, payload) => {
  try {
    const res = await fetch(API.update(id), {
      method: "PUT",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    if (!res.ok) throw new Error("Update failed. Check input values.");
    await fetchStudents();
    clearForm();
    setStatus("Student updated.");
  } catch (err) {
    setStatus(err.message, true);
  }
};

const patchStudent = async (id, payload) => {
  try {
    const res = await fetch(API.patch(id), {
      method: "PATCH",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(payload),
    });
    if (!res.ok) throw new Error("Update failed. Check input values.");
    await fetchStudents();
    clearForm();
    setStatus("Student updated.");
  } catch (err) {
    setStatus(err.message, true);
  }
};

const deleteStudent = async (id) => {
  try {
    const res = await fetch(API.delete(id), { method: "DELETE" });
    if (!res.ok) throw new Error("Delete failed.");
    await fetchStudents();
    if (idInput.value === id) clearForm();
    setStatus("Student deleted.");
  } catch (err) {
    setStatus(err.message, true);
  }
};

const loadStudentIntoForm = async (id) => {
  try {
    const res = await fetch(API.list);
    if (!res.ok) throw new Error("Unable to load students.");
    const students = await res.json();
    const student = students.find((item) => item.id === id);
    if (!student) throw new Error("Student not found.");
    idInput.value = student.id;
    nameInput.value = student.name;
    emailInput.value = student.email;
    ageInput.value = student.age;
    updateBtn.disabled = false;
    setStatus(`Editing ${student.name}.`);
  } catch (err) {
    setStatus(err.message, true);
  }
};

formEl.addEventListener("submit", (event) => {
  event.preventDefault();
  const payload = getPayload();
  if (idInput.value) {
    updateStudent(idInput.value, payload);
    return;
  }
  addStudent(payload);
});

updateBtn.addEventListener("click", () => {
  if (!idInput.value) return;
  const payload = getPatchPayload();
  if (!Object.keys(payload).length) {
    setStatus("Enter at least one field to update.", true);
    return;
  }
  patchStudent(idInput.value, payload);
});

resetBtn.addEventListener("click", clearForm);
refreshBtn.addEventListener("click", fetchStudents);

listEl.addEventListener("click", (event) => {
  const action = event.target.getAttribute("data-action");
  const id = event.target.getAttribute("data-id");
  if (!action || !id) return;
  if (action === "edit") {
    loadStudentIntoForm(id);
  } else if (action === "delete") {
    deleteStudent(id);
  }
});

fetchStudents();
