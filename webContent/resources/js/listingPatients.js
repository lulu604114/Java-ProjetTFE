function modify() {
        // let $groupe = document.getElementById('test');
        //$groupe.disabled = "false";
        let $test = document.getElementById('globalFilter')
        //document.getElementById('test').disabled = true;
        alert($test)
}

function handleLoginRequest(xhr, status, args) {
            if(args.validationFailed || !args.loggedIn) {
                    PF('patientCreation').jq.effect("shake", {times:5}, 100);
            }
            else {
                    PF('patientCreation').hide();
                    $('#loginLink').fadeOut();
            }
    }
