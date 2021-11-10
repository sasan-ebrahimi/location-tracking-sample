# Location Tracker (Sample App)

This is a simple Android project to be delivered as an Interview task.

## Details

 -  Project is implemented in Kotlin.
 -  Room, Hilt, Google Maps, and Coroutines are used in the project.
 -  Detected locations are cached in database and will be shown on map. After closing and reopening the app it fetches most recent locations and shows them on map (100 locations by default).
 -  To prevent overdrawing points on map, data get filtered in ViewModel level so that just new points will be selected in order to be drawn on the map.
 -  A simple Permission screen checks granted permission at the first time of launching the app. 
 
## Preview
<img src="https://github.com/sasan-ebrahimi/location-tracking-sample/blob/main/preview/preview.gif" width="270">
