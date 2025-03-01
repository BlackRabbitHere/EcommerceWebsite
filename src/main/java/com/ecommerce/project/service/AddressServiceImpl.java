package com.ecommerce.project.service;


import com.ecommerce.project.Repositories.AddressRepository;
import com.ecommerce.project.Repositories.UserRepository;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Address;
import com.ecommerce.project.model.User;
import com.ecommerce.project.payload.AddressDTO;
import com.ecommerce.project.util.AuthUtil;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private AuthUtil authUtil;
    @Autowired
    private UserRepository userRepository;

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

    @Override
    public List<AddressDTO> getAddressesByUser(User user) {
        List<Address>addresses=user.getAddresses();
        return addresses.stream()
                .map(address -> modelMapper.map(address,AddressDTO.class)).toList();
    }

    @Override
    public AddressDTO updateAddress(Long addressId, @Valid AddressDTO addressDTO) {
        Address addressFromDB = addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        addressFromDB.setStreet((addressDTO.getStreet()));
        addressFromDB.setBuildingName((addressDTO.getBuildingName()));
        addressFromDB.setCity((addressDTO.getCity()));
        addressFromDB.setPincode((addressDTO.getPincode()));
        addressFromDB.setState((addressDTO.getState()));
        addressFromDB.setCountry((addressDTO.getCountry()));

        Address updatedAddress=addressRepository.save(addressFromDB);
        // update the user addresses list

        User user=addressFromDB.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        user.getAddresses().add(addressFromDB);
        userRepository.save(user);
        return modelMapper.map(updatedAddress,AddressDTO.class);
    }

    @Override
    public String deleteAddress(Long addressId) {
        Address addressFromDB=addressRepository.findById(addressId)
                .orElseThrow(() -> new ResourceNotFoundException("Address", "id", addressId));

        User user=addressFromDB.getUser();
        user.getAddresses().removeIf(address -> address.getAddressId().equals(addressId));
        userRepository.save(user);

        addressRepository.delete(addressFromDB);

        return "Address deleted successfully with addressId: "+ addressId;
    }
}
