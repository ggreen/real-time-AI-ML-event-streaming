


function decorateSentiment(letterResult)
{
    var sentimentHtml = "";

    if( "POSITIVE" == letterResult.sentiment )
        sentimentHtml = "<button class='w3-button w3-block w3-green w3-section' title='Accept'>positive <i class='fa fa-check'></i></button>";
    else
        sentimentHtml = "<button class='w3-button w3-block w3-red w3-section' title='Decline'>negative <i class='fa fa-remove'></i></button>";


    return     sentimentHtml;
}
