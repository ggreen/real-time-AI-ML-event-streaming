


function decorateSentiment(letterResult)
{
    var sentimentHtml = "";

    //w3-block
    if( "POSITIVE" == letterResult.sentiment )
        sentimentHtml = "<button class='w3-button w3-green w3-section' title='Accept'>positive <i class='fa fa-check'></i></button>";
    else
        sentimentHtml = "<button class='w3-button w3-red w3-section' title='Decline'>negative <i class='fa fa-remove'></i></button>";


    return     sentimentHtml;
}
