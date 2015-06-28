var ip = "http://45.55.214.35:3001";
$(document).ready(function(){

    $("#listar_trabajadores").click(function(){
        $("#botonera_buscar, .alert").hide();
        $("#rut_trabajador").val("");

        $.get(ip+"/rrhh/trabajadores", function(json){
            $("#listado_trabajadores .trabajador").remove();
            if(json.error!=-1 && json.length>0){
                for(i in json){
                    $("#listado_trabajadores").append("<tr class='trabajador'><td>"+(parseInt(i)+1)+"</td><td>"+json[i].rut+"</td><td>"+json[i].nombre+" </td><td>"+json[i].email+" </td></tr>")
                    $("#listado_trabajadores").show();
                }
            }else{
                $("#listado_trabajadores").hide();
                $(".row:last").append("<div>holanda</div>");
            }
        });
    });


    $("#btn_buscar_botonera").click(function(e){
        $("#botonera_buscar").show();
    });

    $("#btn_buscar").click(function(){
        var rut = $("#rut_trabajador").val();
        hideKeyboard($("#rut_trabajador"));
         $.get(ip+"/rrhh/trabajadores/"+rut, function(json){
            $("#listado_trabajadores .trabajador").remove();
            $("#listado_trabajadores").hide();
            if(json.error!=1){
                $("#listado_trabajadores").append("<tr class='trabajador'><td>1</td><td>"+json.rut+"</td><td>"+json.nombre+" </td><td>"+json.email+" </td></tr>");
                $("#listado_trabajadores").show();
            }else{

                $(".alert").empty().append("<span>"+json.mensaje+"</span>");
                $(".alert").show();
            }
            
         });
    });

});

//from http://goo.gl/VNXji2
function hideKeyboard(element) {
element.attr('readonly', 'readonly'); // Force keyboard to hide on input field.
element.attr('disabled', 'true'); // Force keyboard to hide on textarea field.
setTimeout(function() {
    element.blur();  //actually close the keyboard
    // Remove readonly attribute after keyboard is hidden.
    element.removeAttr('readonly');
    element.removeAttr('disabled');
}, 100);
}