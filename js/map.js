window.map = null;

Object.assign(window, {
    initMap() {

        window.map = new google.maps.Map(document.getElementById("map"), {
            center: { lat: 37.488167, lng: 127.120589 },
            scrollwheel = false,
            zoom: 8
        });
        
        var marker = new google.maps.Marker({
            map : window.map,
            position : {lat: 37.488167, lng: 127.120589},
        });
                
    }
});