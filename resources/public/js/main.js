'use strict';

// Page Listener
document.addEventListener('DOMContentLoaded', pageLoadedHandler);

function pageLoadedHandler () {
  // Dropdown handlers

  var $dropdowns = getAll('.dropdown:not(.is-hoverable)');

  if ($dropdowns.length > 0) {

    $dropdowns.forEach(toggleIsActive)

    document.addEventListener('click', documentClickHandler)
    document.addEventListener('keydown', documentKeydownHandler)
  }

  /**
   * Handlers
   */
  function documentClickHandler(event) {
    closeDropdowns();
  }

  function documentKeydownHandler (event) {
    var e = event || window.event;

    if (e.keyCode === 27) {
      closeDropdowns();
    }
  }

  /**
   * Functions
   */
  function closeDropdowns () {
    $dropdowns.forEach(function ($el) {
      $el.classList.remove('is-active');
    });
  }


  function toggleIsActive($el) {
    $el.addEventListener('click', function (event) {
      event.stopPropagation();
      $el.classList.toggle('is-active')
    });
  }

  function getAll(selector) {
    return Array.prototype.slice.call(document.querySelectorAll(selector), 0);
  }
}
