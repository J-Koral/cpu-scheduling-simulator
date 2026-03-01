document.addEventListener('DOMContentLoaded', () => {
    const processContainer = document.getElementById("processContainer");
    const addProcessButton = document.getElementById("add-process");
    const runSimulationButton = document.getElementById("run-algorithm");
    const results = document.getElementById("results");

    let processes = [];

    function createProcesses() {
        processContainer.innerHTML = "";
        processes.forEach((process, i) => {
            const row = document.createElement("div");
            row.innerHTML = `
                <div class="process-box">
                    <strong>${process.processId}</strong><br>
                    Arrival: <input type="number" value="${process.arrivalTime}" data-index="${i}" data-field="arrivalTime">
                    Burst: <input type="number" value="${process.burstTime}" data-index="${i}" data-field="burstTime">
                    <button data-delete="${i}">x</button>
                </div>
            `;
            processContainer.appendChild(row);
        });
    }

    addProcessButton.addEventListener("click", () => {
        processes.push({
            processId: "P" + (processes.length + 1),
            arrivalTime: 0,
            burstTime: 0,
            priority:0
        });
        createProcesses();
    });

    processContainer.addEventListener("input", (e) => {
        const i = e.target.dataset.index;
        const field = e.target.dataset.field;
        if(field === "arrivalTime" || field === "burstTime") {
            processes[i][field] = Number(e.target.value);
        }
    });

    processContainer.addEventListener("click", (e) => {
        if(e.target.dataset.delete !== undefined) {
            const i = Number(e.target.dataset.delete);
            processes.splice(i, 1);
            createProcesses()
        }
    });

    runSimulationButton.addEventListener("click", () => {
        const requestBody = {
            algorithm: document.getElementById("algorithm").value,
            quantum: null,
            processes: processes
        };

        fetch("http://localhost:8080/api/simulate", {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(requestBody)
        })
        .then(response => response.json())
        .then(data => {
            results.textContent = JSON.stringify(data, null, 2);
        })
        .catch(error => {
            results.textContent = "Error: " + error;
        });
    });

    addProcessButton.click();
});