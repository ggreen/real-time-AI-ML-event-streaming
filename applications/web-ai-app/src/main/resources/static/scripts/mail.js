
function drawStreamMail(message)
{
       console.log("data: "+message.data);
     var letterResults = JSON.parse(message.data);

    //save results
    pageLetterResults = letterResults;

    $("#ReceivedLettersPanel").html("");

   //for loop
    var letterResultsHtml = "";

    var i=0;

    for (let x in letterResults) {

        var letterResult = letterResults[x];

        if(i == 0)
          drawMainLetter(x);

        var sentimentHtml = "";

        if( "POSITIVE" == letterResult.sentiment )
           sentimentHtml = "<button class='w3-button w3-block w3-green w3-section' title='Accept'>positive <i class='fa fa-check'></i></button>";
        else
            sentimentHtml = "<button class='w3-button w3-block w3-red w3-section' title='Decline'>negative <i class='fa fa-remove'></i></button>";

        var lrHtml = "<a href='javascript:void(0)' class='w3-bar-item w3-button w3-border-bottom test w3-hover-light-grey' onclick='drawMainLetter("+x+");w3_close();' id='firstTab'>"+
                     "<div class='w3-container'>"+
                            "<img class='w3-round w3-margin-right' src='/images/"+letterResult.letter.author+".png' style='width:15%;'><span class='w3-opacity w3-large'>"+letterResult.letter.author+"</span>"+
                            "<h6>Subject: "+letterResult.letter.subject+"</h6>"+
                        "</div>"+
                        "<div class='w3-half'>"+
                         sentimentHtml+
                        "</div>"+
                    "</a>";



        letterResultsHtml +=lrHtml;
        i++;
     }

        $("#ReceivedLettersPanel").html(letterResultsHtml);
                                                                 }

function drawMainLetter(pageLetterResultIndex)
{
    var letterResult =  pageLetterResults[pageLetterResultIndex];
    var dateTime = new Date(letterResult.letter.timeMs);
    var dateText = dateTime.toDateString();


    $("#MailFromText").html(letterResult.letter.author);
    $("#MailSubjectText").html(letterResult.letter.subject);
    $("#MailBodyText").html(letterResult.letter.body);
    $("#MailTimeText").html(dateText);
    $("#MailFromImage").attr("src","images/"+letterResult.letter.author+".png");

//
    var sentimentHtml = "";
    if( "POSITIVE" == letterResult.sentiment )
        sentimentHtml = "<button class='w3-button w3-green w3-section' title='Accept'>positive <i class='fa fa-check'></i></button>";
    else
                sentimentHtml = "<button class='w3-button w3-red w3-section' title='Decline'>negative <i class='fa fa-remove'></i></button>";


            var sentimentBlock = sentimentHtml;
            $("#MailSentimentText").html(sentimentBlock);
}