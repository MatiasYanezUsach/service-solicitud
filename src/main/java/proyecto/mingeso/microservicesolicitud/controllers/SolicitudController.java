package proyecto.mingeso.microservicesolicitud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import proyecto.mingeso.microservicesolicitud.entities.SolicitudEntity;
import proyecto.mingeso.microservicesolicitud.repositories.SolicitudRepository;
import proyecto.mingeso.microservicesolicitud.services.SolicitudService;

import java.util.ArrayList;

@RestController
@RequestMapping("/solicitud")
public class SolicitudController {
    @Autowired
    SolicitudService solicitudService;
    @Autowired
    SolicitudRepository solicitudRepository;

    @PostMapping("/subirSolicitud")
    public ResponseEntity<SolicitudEntity> subirSolicitud(@RequestBody SolicitudEntity solicitud) {
        int cantidadSolicitudes = 0;
        ArrayList<SolicitudEntity> solicitudes = solicitudRepository.findByRut(solicitud.getRut_empleado());
        for (SolicitudEntity solicitados : solicitudes) {
            cantidadSolicitudes++;
        }
        for (int i = 0; i < cantidadSolicitudes; i++) {
            if (solicitudes.get(i).getFecha_cubridora().equals(solicitud.getFecha_cubridora())) {
                return ResponseEntity.badRequest().build();
            }
        }
        SolicitudEntity solicitudFinal = solicitudService.crearSolicitud(solicitud.getRut_empleado(), solicitud.getFecha_cubridora());
        if (solicitudFinal == null) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.ok(solicitudFinal);
        }
    }
}