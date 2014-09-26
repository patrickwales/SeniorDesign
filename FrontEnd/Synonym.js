var synonyms = [ ];
var word;

function synonym(word){
    this.name = word;
    this.frequency = null;
    this.source = null;
}

function buildUrl(word) {    
    return "http://words.bighugelabs.com/api/2/1c65553134e5f345100f083a7868eeab/" + word + "/json";
}

function getSynonyms(word, url, POS) {
    $.ajax({
        url: url,
        dataType: 'jsonp',
        async: false,
        jsonpCallback: 'jsonCallback',
        success: function (data) {
            // Convert JSON to string
            var stringData = JSON.stringify(data);
            alert(stringData);
            
            // Parse and get synonyms that match POS
            var pos = getSynonymsToPOS(stringData, POS);
            alert(pos);
            
            // Make synonym objects and add to synonyms
            createSynonyms(pos);

            // Add to html
            $("#synonyms").append("<li>" + pos + "</li>");
        },
        error: function (xhr, textStatus, errorThrown) {
            alert("readyState: " + xhr.readyState);
            alert("responseText: " + xhr.responseText);
            alert("status: " + xhr.status);
            alert("text status: " + textStatus);
            alert("error thrown: " + errorThrown);
        }
    });
}

function getSynonymsToPOS(stringData, POS){
    var parsedData = JSON.parse(stringData);
    if(POS == "noun"){
        return parsedData.noun.syn;
    } else if(POS == "verb"){
        return parsedData.verb.syn;
    }
}

function createSynonyms(pos){
   // for each synonym word
    for(i=0; i < pos.length; i++){
        alert(pos[i]);
        
        // Create a synonym object
        var syn = synonym(pos[i]);
        synonyms.push(syn);    
    }
}

 
$(document).ready(function () {
    // Create a url to get synonyms from 
    var url = buildUrl("stuff");
    
    // Get noun synonyms 
    getSynonyms("stuff", url, "noun");
});