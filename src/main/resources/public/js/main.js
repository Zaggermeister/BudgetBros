const state = {
  addBudgetData: {
    income: {},
    expenses: {},
    goal: undefined,
  },
  expenses: [],
};

// Mocks

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

  // TODO: Send data here.
  console.log(data);
  state.expenses.push(data);
});

// Startup logic

// TODO: If user has an income, display dashboard, otherwise display add budget. Remember to hide related buttons.
setPageToShow('page-spinner');
setTimeout(() => {
  setPageToShow('page-add-budget');
}, 2000);