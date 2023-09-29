package com.sellerservice.seller_service.service;

import com.identityservice.dtos.IdentityResponseDto;
import com.inventory.inventory.dtos.RequestInventoryDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;

import com.inventory.inventory.dtos.UpdateRequest;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.inventory.inventory.exceptions.SellerNotAvailable;
import com.ktkapp.addressservice.dtos.EmailAddressRequest;
import com.ktkapp.addressservice.dtos.RequestAddressDto;
import com.ktkapp.addressservice.dtos.ResponseAddressDto;
import com.ktkapp.addressservice.exceptions.AddressNotFoundWithEmail;
import com.sellerservice.seller_service.dtos.*;
import com.sellerservice.seller_service.exceptions.SellerNotPresentException;
import com.sellerservice.seller_service.exceptions.UserNotRegiseredAsSellerException;
import com.sellerservice.seller_service.models.Seller;
import com.sellerservice.seller_service.openfeign.AddressFeignClient;
import com.sellerservice.seller_service.openfeign.IdentityFeignClient;
import com.sellerservice.seller_service.openfeign.InventoryFeign;
import com.sellerservice.seller_service.repository.SellerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class SellerServiceImpl implements SellerService{

    private SellerRepository sellerRepository;
    private ModelMapper modelMapper;

    private InventoryFeign inventoryFeign;

    private IdentityFeignClient identityFeignClient;

    private AddressFeignClient addressFeignClient;


    public SellerServiceImpl(SellerRepository sellerRepository, ModelMapper modelMapper, InventoryFeign inventoryFeign,
                             IdentityFeignClient identityFeignClient, AddressFeignClient addressFeignClient) {
        this.sellerRepository = sellerRepository;
        this.modelMapper = modelMapper;
        this.inventoryFeign = inventoryFeign;
        this.identityFeignClient = identityFeignClient;
        this.addressFeignClient = addressFeignClient;
    }

    @Override
    public SellerResponseDto saveSeller(SellerRequestDto sellerRequestDto,String userName) throws SellerNotPresentException {
        IdentityResponseDto identityResponseDto =identityFeignClient.getUserCredentials(userName);
        List<String> authorization= Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if (!authorization.contains("ROLE_SELLER") || !authorization.contains("ROLE_ADMIN")) {
            throw new SellerNotPresentException("Not authorized to save seller details ...");
        }
        Seller seller = new Seller();
        seller.setSellerName(sellerRequestDto.getSellerName());
        seller.setMobile(sellerRequestDto.getMobile());
        Date date = identityResponseDto.getCreatedOn();
        seller.setEmailId(identityResponseDto.getEmailId());
        seller.setCreatedOn(date);
        seller.setUpdatedOn(new Date());

        Seller seller1 = sellerRepository.save(seller);

        return modelMapper.map(seller1, SellerResponseDto.class);
    }


    @Override
    public SellerResponseDto getByEmail(String userName) throws SellerNotPresentException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization= Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if (!authorization.contains("ROLE_SELLER") || !authorization.contains("ROLE_ADMIN")) {
            throw new SellerNotPresentException("Not authorized to get seller details ...");
        }

        Optional<Seller> seller = sellerRepository.findByEmailId(identityResponseDto.getEmailId());
        if(seller.isEmpty()) {
            throw new SellerNotPresentException("Seller Details not saved ,Please save Seller details...");
        }

        return modelMapper.map(seller.get(), SellerResponseDto.class);
    }

    @Override
    public List<ResponseInventoryDto> getAllInventory(String userName) throws SellerNotAvailable {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        return inventoryFeign.getBySellerEmail(identityResponseDto.getEmailId());
    }

    @Override
    public SellerResponseInventoryDto saveInventory(RequestInventoryDto requestInventoryDto, String userName) throws SellerNotPresentException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        requestInventoryDto.setSellerEmailId(identityResponseDto.getEmailId());
        requestInventoryDto.setCreatedOn(identityResponseDto.getCreatedOn());
        requestInventoryDto.setUpdatedOn(new Date());

        Optional<Seller> seller = sellerRepository.findByEmailId(identityResponseDto.getEmailId());

        if(seller.isEmpty()) {
            throw new SellerNotPresentException("Seller Details not Present ,Please save Seller details...");
        }

        Seller seller1 = seller.get();



        seller1.setUpdatedOn(new Date());
        seller1.setProductsRegistered(seller.get().getProductsRegistered()+1);

        Seller seller2 = sellerRepository.save(seller1);
        SellerResponseInventoryDto sellerResponseInventoryDto = new SellerResponseInventoryDto();
        sellerResponseInventoryDto.setSellerResponseDto(modelMapper.map(seller2,SellerResponseDto.class));

        List<String> authorization= Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if (!authorization.contains("ROLE_SELLER") || !authorization.contains("ROLE_ADMIN")) {
            throw new SellerNotPresentException("Not authorized to save Inventory ...");
        }

        ResponseInventoryDto   responseInventoryDto = inventoryFeign.saveInventory(requestInventoryDto);
        sellerResponseInventoryDto.setResponseInventoryDto(responseInventoryDto);

        return sellerResponseInventoryDto;

    }


    @Override
    public ResponseSellerAddressDto saveAddress(RequestAddressDto requestAddressDto, String userName) throws SellerNotPresentException, UserNotRegiseredAsSellerException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization= Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if (!authorization.contains("ROLE_SELLER") || !authorization.contains("ROLE_ADMIN")) {
            throw new SellerNotPresentException("Not authorized to save Address ...");
        }


        EmailAddressRequest emailAddressRequest = new EmailAddressRequest();
        emailAddressRequest.setEmailId(identityResponseDto.getEmailId());

        requestAddressDto.setEmailRequest(emailAddressRequest);

        Optional<Seller> seller = sellerRepository.findByEmailId(identityResponseDto.getEmailId());

        if(seller.isEmpty() ){
            throw new SellerNotPresentException("Please add seller details ... Requested seller not exists...");
        }

        SellerResponseDto sellerResponseDto = modelMapper.map(seller.get(),SellerResponseDto.class);

        ResponseAddressDto responseAddressDto = addressFeignClient.saveAddress(requestAddressDto);

        ResponseSellerAddressDto responseSellerAddressDto = new ResponseSellerAddressDto();
        responseSellerAddressDto.setSellerResponseDto(sellerResponseDto);
        responseSellerAddressDto.setResponseAddressDto(responseAddressDto);

        return responseSellerAddressDto;

    }


    @Override
    public ResponseAddressSellerDto getAllAddressesByEmail(String userName) throws SellerNotPresentException, AddressNotFoundWithEmail {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization= Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if (!authorization.contains("ROLE_SELLER") || !authorization.contains("ROLE_ADMIN")) {
            throw new SellerNotPresentException("Not authorized to get Addresses ...");
        }

        Optional<Seller> seller = sellerRepository.findByEmailId(identityResponseDto.getEmailId());

        if(seller.isEmpty()){
            throw new SellerNotPresentException("Seller is not present...");
        }

        ResponseAddressSellerDto responseAddressSellerDto = new ResponseAddressSellerDto();

        responseAddressSellerDto.setSellerResponseDto(modelMapper.map(seller.get(), SellerResponseDto.class));

        List<ResponseAddressDto> responseAddressDtos = addressFeignClient.getAddresses(identityResponseDto.getEmailId());

        responseAddressSellerDto.setAddresses(responseAddressDtos);

        return responseAddressSellerDto;

    }

    @Override
    public SellerResponseDto getSellerByEmail(String emailId) throws SellerNotPresentException {
        Optional<Seller> seller = sellerRepository.findByEmailId(emailId);

        if(seller.isEmpty() ){
            throw new SellerNotPresentException("Please add seller details ... Requested seller not exists...");
        }

        return modelMapper.map(seller.get(),SellerResponseDto.class);
    }

    @Override
    public ResponseInventoryDto updateInventory(UpdateRequest updateQuantityDto,String userName) throws ProductsStockNotAvailableInInInventory, SellerNotPresentException {
        IdentityResponseDto identityResponseDto = identityFeignClient.getUserCredentials(userName);

        List<String> authorization= Arrays.stream(identityResponseDto.getRoles().split(",")).toList();

        if (!authorization.contains("ROLE_SELLER") || !authorization.contains("ROLE_ADMIN")) {
            throw new SellerNotPresentException("Not authorized to save Address ...");
        }
        updateQuantityDto.setSellerEmailId(identityResponseDto.getEmailId());

        return inventoryFeign.updateInventory(updateQuantityDto);
    }
}
