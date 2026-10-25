package com.grupo7.GestionInventarioAPI.service.impl;

import com.grupo7.GestionInventarioAPI.dto.request.ProveedorRequestDTO;
import com.grupo7.GestionInventarioAPI.dto.request.ProveedorUpdateDTO;
import com.grupo7.GestionInventarioAPI.dto.response.ProveedorResponseDTO;
import com.grupo7.GestionInventarioAPI.exception.RecursoDuplicadoException;
import com.grupo7.GestionInventarioAPI.exception.RecursoNoEncontradoException;
import com.grupo7.GestionInventarioAPI.mapper.ProveedorMapper;
import com.grupo7.GestionInventarioAPI.model.Proveedor;
import com.grupo7.GestionInventarioAPI.repository.ProveedorRepository;
import com.grupo7.GestionInventarioAPI.service.Helper;
import com.grupo7.GestionInventarioAPI.service.ProveedorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProveedorServiceImpl implements ProveedorService {

    private final ProveedorRepository proveedorRepository;
    private final ProveedorMapper proveedorMapper;
    private final Helper helper;

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorResponseDTO> listarActivos() {
        return proveedorRepository.findByActivoTrue()
                .stream()
                .map(proveedorMapper::toDTO)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProveedorResponseDTO obtenerPorId(Integer id) {
        Proveedor proveedor = proveedorRepository
                .findByIdProveedorAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Proveedor no encontrado o inactivo con ID: " + id
                ));

        return proveedorMapper.toDTO(proveedor);
    }

    @Override
    @Transactional
    public ProveedorResponseDTO crear(
            ProveedorRequestDTO dto,
            Integer usuarioId
    ) {
        helper.exigirAdmin(usuarioId);

        if (proveedorRepository.existsByCorreo(dto.getCorreo())) {
            throw new RecursoDuplicadoException(
                    "Ya existe un proveedor con el correo: " + dto.getCorreo()
            );
        }

        Proveedor proveedor = proveedorMapper.toEntity(dto);
        proveedor.setActivo(true);

        Proveedor guardado = proveedorRepository.save(proveedor);

        return proveedorMapper.toDTO(guardado);
    }

    @Override
    @Transactional
    public ProveedorResponseDTO actualizar(
            Integer id,
            ProveedorUpdateDTO dto,
            Integer usuarioId
    ) {
        helper.exigirAdmin(usuarioId);

        Proveedor proveedor = proveedorRepository
                .findByIdProveedorAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Proveedor no encontrado o inactivo con ID: " + id
                ));

        if (proveedorRepository.existsByCorreoAndIdProveedorNot(
                dto.getCorreo(), id)) {
            throw new RecursoDuplicadoException(
                    "Ya existe otro proveedor con el correo: " + dto.getCorreo()
            );
        }

        if(dto.getNombreProveedor() != null){
            proveedor.setNombreProveedor(dto.getNombreProveedor());
        }

        if(dto.getCorreo() != null){
            proveedor.setCorreo(dto.getCorreo());
        }

        if(dto.getTelefono() != null){
            proveedor.setTelefono(dto.getTelefono());
        }

        if(dto.getDireccion() != null){
            proveedor.setDireccion(dto.getDireccion());
        }

        Proveedor actualizado = proveedorRepository.save(proveedor);

        return proveedorMapper.toDTO(actualizado);
    }

    @Override
    @Transactional
    public void eliminar(Integer id, Integer usuarioId) {
        helper.exigirAdmin(usuarioId);

        Proveedor proveedor = proveedorRepository
                .findByIdProveedorAndActivoTrue(id)
                .orElseThrow(() -> new RecursoNoEncontradoException(
                        "Proveedor no encontrado o inactivo con ID: " + id
                ));

        proveedor.setActivo(false);
        proveedorRepository.save(proveedor);
    }
}