function deselectOtherCheckboxes(checkbox) {
                var checkboxes = document.getElementsByName("selectedProfessional");
                for (var i = 0; i < checkboxes.length; i++) {
                    if (checkboxes[i] !== checkbox) {
                        checkboxes[i].checked = false;
                    }
                }
            }
deselectOtherCheckboxes();
