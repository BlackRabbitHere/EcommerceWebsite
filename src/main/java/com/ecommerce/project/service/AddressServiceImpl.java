package com.ecommerce.project.service;


import com.ecommerce.project.Repositories.AddressRepository;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.util.AuthUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private AuthUtil authUtil;

    @Override
    public AddressDTO createAddress(AddressDTO addressDTO, User user) {
        Address address=modelMapper.map(addressDTO, Address.class);

        List<Address> addressList=user.getAddresses();
        addressList.add(address);
        user.setAddresses(addressList);

        address.setUser(user);

        Address savedAddress=addressRepository.save(address);

        return modelMapper.map(savedAddress, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddresses() {
        List<Address>addresses=addressRepository.findAll();
        return addresses.stream()
                .map(address -> modelMapper.map(address,AddressDTO.class))
                .toList();
    }

    @Override
    public AddressDTO findAddressById(Long addressId) {
        Address address=addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));
        return modelMapper.map(address,AddressDTO.class);
    }
}
