var Project1 = ( function() {

    return {

        init: function() {
            
            $("#version").html( "jQuery Version: " + $().jquery );

        },
        
        submitSearchForm: function() {

            if ( $("#sessionid").val() === "" ) {

                alert("You must enter a search parameter!  Please try again.");
                return false;

            }

            $.ajax({

                url: 'Registration',
                method: 'GET',
                data: $('#searchform').serialize(),

                success: function(response) {

                    $("#resultsarea").html(response);

                }

            });

            return false;

        }

    };

}());