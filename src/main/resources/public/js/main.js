function getCookie(cname) {
  var name = cname + "=";
  var decodedCookie = decodeURIComponent(document.cookie);
  var ca = decodedCookie.split(';');
  for(var i = 0; i <ca.length; i++) {
    var c = ca[i];
    while (c.charAt(0) == ' ') {
      c = c.substring(1);
    }
    if (c.indexOf(name) == 0) {
      return c.substring(name.length, c.length);
    }
  }
      window.location = window.location.protocol + '//' + window.location.hostname + (window.location.port ? `:${window.location.port}`: '') + '/signin';
  return "";
}

let loggedInUserId = getCookie("budgetBro");

const state = {
  addBudgetData: {},
  userInfo: {},
  expenses: [],
};

// Constants

const pageIds = [
  'page-spinner',
  'page-add-budget',
  'page-dashboard',
];

const goals = [
  '50/30/20',
  '40/40/20',
  '40/30/30',
];

const endpoints = {};

// Navbar buttons

const menuBurger = document.getElementById('menu-burger');

// Sidebar buttons

const sidebar = document.getElementById('sidebar');
const sideBarCloseButton = document.getElementById('sidebar-close-button');
const sidebarDashboardButton = document.getElementById('sidebar-dashboard');
const sidebarAddBudgetButton = document.getElementById('sidebar-add-budget');
const sidebarAddExpensesButton = document.getElementById('sidebar-add-expenses');

// Forms

const addBudgetForm = document.getElementById('add-budget-form');
const addExpenseForm = document.getElementById('add-expenses-form');

// Table

const expenseTableBody = document.getElementById('table-body');
const expenseTableHeading = document.getElementById('expense-table-heading');
const expenseTable = document.getElementById('expense-table');

// Charts

let pieChart = null;
let barChart = null;

const chart1 = document.getElementById('chart-1').getContext('2d');
const chart2 = document.getElementById('chart-2').getContext('2d');

Chart.defaults.global.defaultFontFamily = 'Roboto';
Chart.defaults.global.defaultFontColor = 'rgb(135, 138, 143)';

// Functions

function setPageToShow(pageToShow) {

  for (let i = 0; i < pageIds.length; i++) {

    const page = document.getElementById(pageIds[i]);

    if (pageToShow === pageIds[i]) {
      page.classList.remove('hidden');
      continue;
    }

    page.classList.add('hidden');
  }
}

function getExpenses() {

  fetch(`http://localhost:8080/api/v1/expenses/${loggedInUserId}`, {
    method: 'GET',
    headers: {
      'Accept': 'application/json'
    },
  })
  .then((res) => res.json())
  .then((json) => {

    if (!json.length) {
      return;
    }
  
    expenseTableHeading.innerHTML = 'Expenses';
    expenseTable.classList.remove('hidden');
  
    json.forEach((expense) => {
      addExpense(expense, expenseTableBody);
    });
  })
}

function getExpensesPerCategory() {

  fetch(`http://localhost:8080/api/v1/totalExpensesPerCategory/${loggedInUserId}`, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
    }
  })
  .then((res) => res.json())
  .then((json) => {
    addExpenseToPieChart(json);
  })
  .catch((err) => console.warn(err))
}

function getUserBudget() {

  return fetch(`http://localhost:8080/api/v1/budget/${loggedInUserId}`, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
    }
  })
}

function addExpense(expense, tableBody) {

  state.expenses.push(expense);

  const tr = document.createElement('tr');

  const tds = Array(4).fill().map((_, i) => document.createElement('td'));

  tds[0].setAttribute('data-label', 'Name');
  tds[1].setAttribute('data-label', 'Type');
  tds[2].setAttribute('data-label', 'Value');
  tds[3].setAttribute('data-label', 'Delete');

  const textNode1 = document.createTextNode(expense.expenseName);
  const textNode2 = document.createTextNode(expense.category);
  const textNode3 = document.createTextNode(expense.amount);

  tds[0].appendChild(textNode1);
  tds[1].appendChild(textNode2);
  tds[2].appendChild(textNode3);

  const img = document.createElement('img');
  img.setAttribute('src', 'assets/bin.png');
  img.addEventListener('click', removeExpense(expense, tr));

  tds[3].appendChild(img);

  tds.forEach((td) => tr.appendChild(td));

  tableBody.appendChild(tr);

  if (tr.parentNode.children) {
    expenseTableHeading.innerHTML = 'Expenses';
    expenseTable.classList.remove('hidden');
  }
}

function removeExpense(expense, tr) {

  return (e) => {

    fetch(`http://localhost:8080/api/v1/expense/${loggedInUserId}/${expense.creationId}`, {
      method: 'DELETE',
      headers: {
        'Accept': 'application/json'
      }
    })
    .then((res) => res.json())
    .then((json) => {

      state.expenses = state.expenses.filter((item) => item.creationId !== expense.creationId);

      if (tr.parentNode.children.length - 1 === 0) {
        expenseTableHeading.innerHTML = 'No detailed expenses';
        expenseTable.classList.add('hidden');
      }
  
      removeExpenseFromPieChart(json[0]);
      removeExpenseFromBarChart(json[1]);
      tr.parentNode.removeChild(tr);
    })
    .catch((err) => console.warn(err))
  }
}

function getHighestCreationId(expenses) {
  let creationId = -1;
  expenses.forEach((expense) => {
    let creationNumId = expense.creationId - 0;
    if (creationNumId > creationId) {
      creationId = creationNumId;
    }
  });
  return creationId;
}

function addExpenseToPieChart(chartData) {

  let pieChartData = chartData;

  const charDataArr = [];

  for (const key in pieChartData) {
    charDataArr.push(pieChartData[key])
  }

  pieChart.data.datasets[0].data = charDataArr;
  pieChart.update();
}

function removeExpenseFromPieChart(chartData) {

  let pieChartData = chartData;

  const charDataArr = [];

  for (const key in pieChartData) {
    charDataArr.push(pieChartData[key])
  }

  pieChart.data.datasets[0].data = charDataArr;
  pieChart.update();
}

function addExpenseToBarChart(chartData) {

  let barChartData = chartData;

  const charDataArr = [];

  for (const key in barChartData) {
    charDataArr.push(barChartData[key])
  }

  barChart.data.datasets[0].data = charDataArr;
  barChart.update();
}

function removeExpenseFromBarChart(chartData) {

  let barChartData = chartData;

  const charDataArr = [];

  for (const key in barChartData) {
    charDataArr.push(barChartData[key])
  }

  barChart.data.datasets[0].data = charDataArr;
  barChart.update();
}

// Event listeners
document.getElementById("btnLogout").addEventListener('click', () => {
    document.cookie = 'budgetBro=; expires=Thu, 01 Jan 1970 00:00:01 GMT;';
})

menuBurger.addEventListener('click', () => {
  sidebar.classList.add('sidebar-show')
});

sideBarCloseButton.addEventListener('click', () => {
  sidebar.classList.remove('sidebar-show');
});

sidebarDashboardButton.addEventListener('click', () => {
  setPageToShow('page-dashboard');
  sidebar.classList.remove('sidebar-show');
});

sidebarAddBudgetButton.addEventListener('click', () => {
  setPageToShow('page-add-budget');
  sidebar.classList.remove('sidebar-show');
});

addBudgetForm.addEventListener('submit', (e) => {
  e.preventDefault();
  const data = state.addBudgetData;
  const inputs = document.getElementsByClassName('add-budget-form-input');

  const salarayIncome = inputs[0].value;
  const otherIncome = inputs[1].value;

  const totalIncome = parseFloat(salarayIncome) + parseFloat(otherIncome);
  
  data.budgetIncomeAmount = totalIncome + '';

  // TODO: Get real id after successful login.
  data.userId = loggedInUserId;

  for (let i = 2; i < inputs.length; i++) {

    const input = inputs[i];
    const inputName = input.id.split('-')[3];

    data[inputName] = input.value;
  }

  const radioInputs = document.getElementsByClassName('add-budget-form-input-radio');

  for (let i = 0; i < radioInputs.length; i++) {
    radioInputs[i].checked && (data.savingsGoal = goals[i]);
  }

  fetch('http://localhost:8080/api/v1/budget', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    body: JSON.stringify(data)
  })
  .then((res) => res.text())
  .then(() => {
    setPageToShow('page-dashboard');
    sidebarDashboardButton.classList.remove('hidden');
    getExpenses();
    getExpensesPerCategory();
    getBudgetLeftPerCategory();
  })
  .catch((err) => console.warn(err))
});

addExpenseForm.addEventListener('submit', (e) => {
  e.preventDefault();
  const data = {};
  const inputs = document.getElementsByClassName('add-expense-form-input');

  data.userId = loggedInUserId;

  data.expenseName = inputs[0].value;
  data.category = inputs[1].value;
  data.amount = inputs[2].value;

  data.creationId = getHighestCreationId(state.expenses) + 1;

  fetch('http://localhost:8080/api/v1/expense', {
    method: 'POST',
    headers: {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
    },
    body: JSON.stringify(data)
  })
  .then((res) => res.json())
  .then((json) => {
    addExpenseToPieChart(json[0]);
    addExpenseToBarChart(json[1]);
  })
  .catch((err) => console.warn(err))

  data.name = inputs[0].value;
  data.type = inputs[1].value;
  data.value = inputs[2].value;

  addExpense(data, expenseTableBody);
});

function getBiggestBudget(json) {

  let biggestBudget = json.budgetPersonalAmount - 0;

  if (json.budgetHouseholdAmount - 0 > biggestBudget) {
    biggestBudget = json.budgetHouseholdAmount - 0
  }

  if (json.budgetDeptAmount - 0 > biggestBudget) {
    biggestBudget = json.budgetDeptAmount - 0
  }

  if (json.budgetOtherAmount - 0 > biggestBudget) {
    biggestBudget = json.budgetOtherAmount - 0
  }

  return biggestBudget;
}

function getBudgetLeftPerCategory() {

  fetch(`http://localhost:8080/api/v1/totalBudgetLeftPerCategory/${loggedInUserId}`, {
    method: 'GET',
    headers: {
      'Accept': 'application/json',
    }
  })
  .then((res) => res.json())
  .then((json) => {
    addExpenseToBarChart(json);
  })
  .catch((err) => console.warn(err))
}

// Startup logic

setPageToShow('page-spinner');

getUserBudget()
  .then((res) => {
    return res.json()
  })
  .then((json) => {

    if (!!json.budgetId) {
      setPageToShow('page-dashboard');
      sidebarAddBudgetButton.classList.add('hidden');
    }
    else {
      setPageToShow('page-add-budget');
      sidebarDashboardButton.classList.add('hidden');
    }

    const biggestBudget = getBiggestBudget(json);

    pieChart = new Chart(chart1, {
      type: 'pie',
      data: {
        datasets: [
          {
            data: [0, 0, 0, 0],
            backgroundColor: [
              '#FE7191',
              '#4AC0C0',
              '#4D72DE',
              '#FFCE56'
            ],
          },
        ],
        labels: [
          'Personal',
          'Household',
          'Debt',
          'Other'
        ]
      },
      options: {
        responsive: true,
        legend: {
          display: true,
          position: 'left',
          labels: {
            fontColor: 'rgb(135, 138, 143)'
          }
        }
      }
    });
    
    barChart = new Chart(chart2, {
      type: 'horizontalBar',
      data: {
        labels: ['Personal', 'Household', 'Debt', 'Other'],
        datasets: [{
          data: [0, 0, 0, 0],
          backgroundColor: [
            '#FE7191',
            '#4AC0C0',
            '#4D72DE',
            '#FFCE56'
          ],
        }]
      },
      options: {
        legend: {
          display: false,
          labels: {
            fontColor: 'rgb(135, 138, 143)'
          }
        },
        scaleShowLabels: false,
        scales: {
          xAxes: [{
            ticks: {
              suggestedMin: 0,
              suggestedMax: biggestBudget
            }
          }]
        }
      }
    });

    if (json.budgetId) {

      getExpenses();
      getExpensesPerCategory();
      getBudgetLeftPerCategory();
    }
  })
  .catch((err) => console.warn(err))
