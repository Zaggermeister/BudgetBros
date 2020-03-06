const state = {
  showMenu: false,
};

const menuBurger = document.getElementById('menu-burger');
const sideBarCloseButton = document.getElementById('sidebar-close-button');
const sidebar = document.getElementById('sidebar');

menuBurger.addEventListener('click', () => {
  sidebar.classList.add('sidebar-show')
});

sideBarCloseButton.addEventListener('click', () => {
  sidebar.classList.remove('sidebar-show');
})