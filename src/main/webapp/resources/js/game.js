/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {


    $('#bet').TouchSpin({
        min: 1,
        max: 100,
        step: 1,
        decimals: 2
    });

    $('#btn-new-game').click(function () {
        window.location.href = ctx + '/start_game?bet=' + $('#bet').val();
        return false;
    });

    $('#btn-hit').click(function () {
        window.location.href = ctx + '/hit';
        return false;
    });
    $('#btn-stand').click(function () {
        window.location.href = ctx + '/stand';
        return false;
    });

});
