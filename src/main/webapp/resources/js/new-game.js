/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(function () {
 
 $("#bet").TouchSpin({
                min: 1,
                max: 100,
                step: 1
            });
            
$('#btn-new-game').click(function() {
    
    window.location.href = ctx + '/new_game?bet=10';
    return false;
});
});
