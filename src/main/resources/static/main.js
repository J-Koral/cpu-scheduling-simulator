const processes = []

let processCounter = 1

function openModal() {
    document.getElementById('modal').classList.add('active')
    document.getElementById('arrival-time').focus()
    document.getElementById('modal-error').textContent = ''
}

function closeModal() {
    document.getElementById('modal').classList.remove('active')
    // clear all the inputs
    document.getElementById('arrival-time').value = ''
    document.getElementById('burst-time').value = ''
    document.getElementById('priority').value = ''
    document.getElementById('modal-error').textContent = ''
}

function handleOverlayClick(e) {
    if(e.target === document.getElementById('modal')) {
        closeModal()
    }
}

function addProcess() {
    // get the arrival and burst time
    const arrivalTime = document.getElementById('arrival-time').value
    const burstTime = document.getElementById('burst-time').value
    const priority = document.getElementById('priority').value

    // check if fields are empty
    if(arrivalTime === '' || burstTime === '') {
        document.getElementById('modal-error').textContent = 'Arrival Time or Burst Time is required.'
        return;
    }

    // build the process
    const process = {
        processId: 'P' + processCounter,
        arrivalTime: Number(arrivalTime),
        burstTime: Number(burstTime),
        priority: priority !== '' ? Number(priority) : 0
    }

    // put the new process in the process array
    processes.push(process)
    processCounter++

    // render the process as a card in the list
    renderProcessCard(process)

    // close the modal
    closeModal()
}

function renderProcessCard(process) {
    const card = document.createElement('div')
    card.className = 'process-card'
    card.id = 'card-' + process.processId
    card.innerHTML = `
        <strong>${process.processId}</strong>
        <span>Arrival: ${process.arrivalTime}</span>
        <span>Burst: ${process.burstTime}</span>
        <span>Priority: ${process.priority}</span>
        <button class="btn-delete" onclick="deleteProcess('${process.processId}')">Delete</button>
    `
    document.getElementById('process-list').appendChild(card)
}

function deleteProcess(processId) {
    // remove the process from the array
    const index = processes.findIndex(p => p.processId === processId)
    if (index !== -1) {
        processes.splice(index, 1)
    }

    // remove the card from the screen
    const card = document.getElementById('card-' + processId)
    if (card) {
        card.remove()
    }
}

// shows the quantum input only when RR is selected
function handleAlgorithmChange() {
    const algorithm = document.getElementById('algorithm').value
    const quantumGroup = document.getElementById('quantum-group')

    if(algorithm === 'RR') {
        quantumGroup.classList.add('visible')
    } else {
        quantumGroup.classList.remove('visible')
    }
}

async function runAlgorithm() {
    if(processes.length === 0) {
        alert('Please add at least one process first.')
        return
    }

    const algorithm = document.getElementById('algorithm').value
    const quantum = algorithm === 'RR' ? Number(document.getElementById('quantum').value) : null

    // build the request body the backend needs
    const requestBody = {
        algorithm: algorithm,
        quantum: quantum,
        processes: processes
    }

    console.log('Sending JSON to the backend:', requestBody)

    try {
        const response = await fetch('http://localhost:8080/api/simulate', {
            method: 'POST',
            headers: {'Content-Type': 'application/json'},
            body: JSON.stringify(requestBody)
        })

        if (!response.ok) {
            throw new Error('Server error: ' + response.status)
        }

        const data = await response.json()
        console.log('Received from backend:', data)

        renderResults(data)

    } catch (error) {
        alert('Failed to connect to backend. Make sure your server is running.\n\n' + error.message)
    }
}

function renderResults(data) {
    // show the results section
    document.getElementById('results').style.display = 'block'

    renderGanttChart(data.ganttChart)
    renderProcessTable(data.processTable)
    renderAverages(data)
}

function renderGanttChart(ganttChart) {
    const container = document.getElementById('gantt-chart')
    container.innerHTML = ''

    // compute total time span so we can fill the container
    const totalTime = ganttChart[ganttChart.length - 1].end
    const MIN_WIDTH = 800
    const MIN_SCALE = 48
    const SCALE = Math.max(MIN_SCALE, Math.floor(MIN_WIDTH / totalTime))

    ganttChart.forEach((block, index) => {

        // check if there's an idle gap before this block
        if (index > 0) {
            const prevBlock = ganttChart[index - 1]
            const gap = block.start - prevBlock.end

            if (gap > 0) {
                // put an idle block to represent idle time
                const idleDiv = document.createElement('div')
                idleDiv.className = 'gantt-block'
                idleDiv.style.minWidth = (gap * SCALE) + 'px'
                idleDiv.innerHTML = `
                    <div class="gantt-bar gantt-idle">Idle</div>
                    <span class="gantt-time">${prevBlock.end}</span>
                `
                container.appendChild(idleDiv)
            }
        }

        // render the actual process block
        const div = document.createElement('div')
        div.className = 'gantt-block'
        const duration = block.end - block.start
        div.style.minWidth = (duration * SCALE) + 'px'
        div.innerHTML = `
            <div class="gantt-bar">${block.processId}</div>
            <span class="gantt-time">${block.start}</span>
        `

        // add the final end time label after the last block
        if (index === ganttChart.length - 1) {
            const endLabel = document.createElement('span')
            endLabel.className = 'gantt-time'
            endLabel.style.alignSelf = 'flex-end'
            endLabel.textContent = block.end
            container.appendChild(div)
            container.appendChild(endLabel)
            return
        }

        container.appendChild(div)
    })
}

function renderProcessTable(processTable) {
    const tbody = document.getElementById('process-table-body')
    tbody.innerHTML = ''

    processTable.forEach(p => {
        const row = document.createElement('tr')
        row.innerHTML = `
            <td>${p.processId}</td>
            <td>${p.arrivalTime}</td>
            <td>${p.burstTime}</td>
            <td>${p.priority}</td>
            <td>${p.waitingTime}</td>
            <td>${p.turnaroundTime}</td>
        `
        tbody.appendChild(row)
    })
}

function renderAverages(data) {
    document.getElementById('averages').innerHTML = `
        <div class="avg-card">
            Avg Waiting Time
            <strong>${data.avgWaitingTime.toFixed(2)}</strong>
        </div>
        <div class="avg-card">
            Avg Turnaround Time
            <strong>${data.avgTurnaroundTime.toFixed(2)}</strong>
        </div>
        <div class="avg-card">
            Throughput
            <strong>${data.throughput.toFixed(4)}</strong>
        </div>
    `
}













