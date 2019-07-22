$(document).ready(function () {
   var categoryElements = $('.category-element');
   var quantityInput = $('#quantity');
   var institutionElements = $('.institution-element');
   var streetInput = $('#street');
   var cityInput = $('#city');
   var zipCodeInput = $('#zip-code');
   var phoneInput = $('#phone-number');
   var dateInput = $('#date');
   var timeInput = $('#time');
   var commentInput = $('#comment');

   var quantityCategoriesSummary = $('#quantity-categories-summary')[0];
   var institutionSummary = $('#institution-summary')[0];
   var addressSummary = $('#address-summary')[0];
   var dateTimeCommentSummary = $('#date-time-comment-summary')[0];

   var btnToSummary = $('#btn-to-summary');

   btnToSummary.on('click', function () {
      console.log(streetInput);
      setQuantityCategorySummary();
      setInstitutionSummary();
      setAddressSummary();
      setDateTimeCommentSummary();
   });

   function setDateTimeCommentSummary() {
      dateTimeCommentSummary.children[0].innerText = dateInput.val();
      dateTimeCommentSummary.children[1].innerText = timeInput.val();
      dateTimeCommentSummary.children[2].innerText = commentInput.val() ? commentInput.val() : 'brak uwag';
   }

   function setAddressSummary() {
      addressSummary.children[0].innerText = streetInput.val();
      addressSummary.children[1].innerText = cityInput.val();
      addressSummary.children[2].innerText = zipCodeInput.val();
      addressSummary.children[3].innerText = phoneInput.val();
   }

   function setInstitutionSummary() {
      institutionElements.each(function (index, element) {
         var input = element.firstElementChild;
         if(input.checked) {
            var name = element.lastElementChild.firstElementChild.innerText;
            institutionSummary.innerText = 'Dla fundacji ' + name;
         }
      });
   }

   function setQuantityCategorySummary() {
      quantityCategoriesSummary.innerHTML = quantityInput.val() + defineCorrectForm(quantityInput.val()) + 'darów w kategoriach:';
      addCategoriesToSummary();
   }
   function defineCorrectForm(quantity) {
      if(quantity == 1) return ' worek ';
      if(quantity > 1 && quantity < 5) return ' worki ';
      return ' worków ';
   }
   function addCategoriesToSummary() {
      categoryElements.each(function (index, element) {
         var input = element.firstElementChild;
         if(input.checked) {
            var description = element.lastElementChild.innerText;
            var category = document.createElement('div');
            category.innerText ='- ' + description;
            quantityCategoriesSummary.appendChild(category);
         }
      });
   }
});