const state = {
  addBudgetData: {
    income: {},
    expenses: {},
    goal: undefined,
  },
  userInfo: {},
  expenses: [],
};

// Mocks

const userInfoMock = {
  id: 0,
  name: 'Bitzer',
  surname: 'Quack',
  email: 'derp@gmail.com',
  income: '20000',
  expenses: {
    personal: '1200',
    household: '2500',
    debt: '10000',
    other: '500'
  },
  goal: '50/30/20'
}

const expensesMock = [
  {
    id: 0,
    name: 'Chicken',
    type: 'Personal',
    value: '100'
  },
  {
    id: 1,
    name: 'Shoes',
    type: 'Personal',
    value: '500'
  },
  {
    id: 2,
    name: 'Electricity',
    type: 'Household',
    value: '1000'
  },
  {
    id: 3,
    name: 'Mortgage',
    type: 'Debt',
    value: '5000'
  },
];

// Constants

const pageIds = [
  'page-spinner',
  'page-add-budget',
  'page-add-expenses',
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

const chart1 = document.getElementById('chart-1').getContext('2d');
const chart2 = document.getElementById('chart-2').getContext('2d');

Chart.defaults.global.defaultFontFamily = 'Roboto';
Chart.defaults.global.defaultFontColor = 'rgb(135, 138, 143)';

// TODO: Will need to move this down after startup logic.
let pieChart = new Chart(chart1, {
  type: 'pie',
  data: {
    datasets: [
      {
        data: getUserExpenses(userInfoMock),
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

let barChart = new Chart(chart2, {
  type: 'horizontalBar',
  data: {
    labels: ['Income'],
    datasets: [{
      data: getUserBudgetedIncomeLeft(userInfoMock),
      backgroundColor: '#4D72DE'
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
          suggestedMax: getUserBudgetedIncome(userInfoMock)
        }
      }]
    }
  }
});

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

  return new Promise((res, rej) => {
    setTimeout(() => res(expensesMock), 3000);
  });
}

function addExpense(expense, tableBody) {

  state.expenses.push(expense);

  const tr = document.createElement('tr');

  const tds = Array(4).fill().map((_, i) => document.createElement('td'));

  tds[0].setAttribute('data-label', 'Name');
  tds[1].setAttribute('data-label', 'Type');
  tds[2].setAttribute('data-label', 'Value');
  tds[3].setAttribute('data-label', 'Delete');

  const textNode1 = document.createTextNode(expense.name);
  const textNode2 = document.createTextNode(expense.type);
  const textNode3 = document.createTextNode(expense.value);

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

    // TODO: Make request to delete from db using expense.id.

    state.expenses = state.expenses.filter((item) => item.id !== expense.id);

    // TODO: Update chartJS here.

    if (tr.parentNode.children.length - 1 === 0) {
      expenseTableHeading.innerHTML = 'No detailed expenses';
      expenseTable.classList.add('hidden');
    }

    removeExpenseFromPieChart(expense);
    removeExpenseFromBarChart(expense);
    tr.parentNode.removeChild(tr);
  }
}

function getHighestExpenseId(expenses) {
  let highestId = -1;
  expenses.forEach((expense, i) => expense.id > highestId && (highestId = expense.id));
  return highestId;
}

// userInfoMock.expenses.personal,
// userInfoMock.expenses.household,
// userInfoMock.expenses.debt,
// userInfoMock.expenses.other

function addExpenseToPieChart(expense) {

  const expenseType = expense.type.toLowerCase();
  const newData = pieChart.data.datasets[0].data.slice();

  switch (expenseType) {
    case "personal":
      newData[0] = (newData[0] - 0) + (expense.value - 0) + '';
      break;
    case "household":
      newData[1] = (newData[1] - 0) + (expense.value - 0) + '';
      break;
    case "debt":
      newData[2] = (newData[2] - 0) + (expense.value - 0) + '';
      break;
    case "other":
      newData[3] = (newData[3] - 0) + (expense.value - 0) + '';
      break;
    default:
      break;
  }

  pieChart.data.datasets[0].data = newData;
  pieChart.update();
}

function removeExpenseFromPieChart(expense) {

  const expenseType = expense.type.toLowerCase();
  const newData = pieChart.data.datasets[0].data.slice();

  switch (expenseType) {
    case "personal":
      newData[0] = (newData[0] - 0) - (expense.value - 0) + '';
      break;
    case "household":
      newData[1] = (newData[1] - 0) - (expense.value - 0) + '';
      break;
    case "debt":
      newData[2] = (newData[2] - 0) - (expense.value - 0) + '';
      break;
    case "other":
      newData[3] = (newData[3] - 0) - (expense.value - 0) + '';
      break;
    default:
      break;
  }

  pieChart.data.datasets[0].data = newData;
  pieChart.update();
}

function addExpenseToBarChart(expense) {

  const newData = barChart.data.datasets[0].data.slice();
  newData[0] -= expense.value;

  barChart.data.datasets[0].data = newData;
  barChart.update();
}

function removeExpenseFromBarChart(expense) {

  const newData = barChart.data.datasets[0].data.slice();
  newData[0] = (newData[0] - 0) + (expense.value - 0);

  barChart.data.datasets[0].data = newData;
  barChart.update();
}

function getUserExpenses(user) {

  const userExpenses = [];

  for (const key in user.expenses) {
    userExpenses.push(user.expenses[key])
  }

  return userExpenses;
}

function getUserBudgetedIncome(user) {
  
  // Goal ex. 50/30/20 (Expenses, Wants, Savings)
  
  const expensesPercentage = user.goal.split('/')[0] / 100;
  return user.income * expensesPercentage;
}

function getUserBudgetedIncomeLeft(user) {
  
  const totalExpenses = getUserExpenses(user).reduce((total, val) => (total - 0) + (val - 0));
  return [getUserBudgetedIncome(user) - totalExpenses];
}

// Event listeners

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

sidebarAddExpensesButton.addEventListener('click', () => {
  setPageToShow('page-add-expenses');
  sidebar.classList.remove('sidebar-show');
});

addBudgetForm.addEventListener('submit', (e) => {
  e.preventDefault();
  const data = state.addBudgetData;
  const inputs = document.getElementsByClassName('add-budget-form-input');

  for (let i = 0; i < inputs.length; i++) {

    const input = inputs[i];
    const inputName = input.id.split('-')[3];

    if (i < 2) {
      data.income[inputName] = input.value;
      continue;
    }

    data.expenses[inputName] = input.value;
  }

  const radioInputs = document.getElementsByClassName('add-budget-form-input-radio');

  for (let i = 0; i < radioInputs.length; i++) {
    radioInputs[i].checked && (data.goal = goals[i]);
  }

  // TODO: Send data here.
  console.log(state.addBudgetData);
});

addExpenseForm.addEventListener('submit', (e) => {
  e.preventDefault();
  const data = {};
  const inputs = document.getElementsByClassName('add-expense-form-input');

  for (let i = 0; i < inputs.length; i++) {

    const input = inputs[i];
    const inputName = input.id.split('-')[2];

    data[inputName] = input.value;
  }

  data.id = getHighestExpenseId(state.expenses) + 1;

  // TODO: Send data here.
  console.log(data);

  // TODO: Don't call addExpense here, push to an array, when you switch to the dashboard (onclick), then call it on the array.
  addExpense(data, expenseTableBody);
  setTimeout(() => addExpenseToPieChart(data), 5000);
  setTimeout(() => addExpenseToBarChart(data), 5000);
  console.log(state.expenses);
});

// Startup logic

// TODO: If user has an income, display dashboard, otherwise display add budget. Remember to hide related buttons.

setPageToShow('page-spinner');
getExpenses().then((expenses) => {

  setPageToShow('page-add-budget');

  if (!expenses.length) {
    return;
  }

  expenseTableHeading.innerHTML = 'Expenses';
  expenseTable.classList.remove('hidden');

  expenses.forEach((expense, i) => {
    addExpense(expense, expenseTableBody);
  });
});