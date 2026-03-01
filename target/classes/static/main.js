document.addEventListener('DOMContentLoaded', () => {
    const processContainer = document.getElementById("processContainer");
    const addProcessButton = document.getElementById("add-process");
    const runSimulationButton = document.getElementById("run-simulation");
    const results = document.getElementById("results");

    let processes = [];

    function createProcesses() {
        processContainer.innerHTML = "";
        processes.forEach((process, index) => {
            const row = document.createElement("div");
            row.innerHTML = `
                ProcessId: <input value="${process.id}" data-index="${index}" data-field="processId">
                Arrival: <input type="number" value="${process.arrivalTime}" data-index="${index}" data-field="arrivalTime">
                Burst: <input type="number" value="${process.burstTime}" data-index="${index}" data-field="burstTime">
                Priority: <input type="number" value="${process.priority}" data-index="${index}" data-field="priority">
                <button data-delete="${index}">x</button>
            `;
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
        const index = e.target.dataset.index;
        const field = e.target.dataset.field;
        processes[index][field] = field == "processId" ? e.target.value : Number(e.target.value);
    });

    processContainer.addEventListener("click", (e) => {
        if(e.target.dataset.delete !== undefined) {
            const index = Number(e.target.dataset.delete);
            processes.splice(index, 1);
            createProcesses()
        }
    });

    runSimulationButton.addEventListener("click", () => {
        const requestBody = {
            algorithm: document.getElementById("algorithm").value,
            quantum: null,
            processes: processes
        };

        fetch("http://localhost:8000/api/simulate", {
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