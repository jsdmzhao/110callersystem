<script type="text/javascript">
      var markers = [];
      function rMarker(){
        var bounds = map.getBounds();
        var southWest = bounds.getSouthWest();
        var northEast = bounds.getNorthEast();
        var lngSpan = northEast.lng() - southWest.lng();
        var latSpan = northEast.lat() - southWest.lat();
       
        for (var i = 0; i < 10; i++) {
            var point = new GLatLng(southWest.lat() + latSpan * Math.random(),
                southWest.lng() + lngSpan * Math.random());
            marker = new GMarker(point);
            map.addOverlay(marker);
            markers[i] = marker;
        }
        $(markers).each(function(i,marker){
          GEvent.addListener(marker, "click", function(){
              map.panTo(marker.getLatLng());
          });
        });
      }
      $(document).ready(function(){
           if (GBrowserIsCompatible()) {
            map = new GMap2($("#map").get(0));
            map.addControl(new GLargeMapControl());
            map.addControl(new GMapTypeControl());
            map.setCenter(new GLatLng(30.67,104.06),13);
            rMarker();
            }
      });
      
</script>
<div style="display:block;border:solid #d9d9d9 1px;height:500px;" id="map"></div>
