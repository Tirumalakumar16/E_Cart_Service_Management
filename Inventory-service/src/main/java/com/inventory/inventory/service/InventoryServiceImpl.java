package com.inventory.inventory.service;

import com.inventory.inventory.dtos.UpdateRequest;
import com.inventory.inventory.dtos.RequestInventoryDto;
import com.inventory.inventory.dtos.ResponseInventoryDto;
import com.inventory.inventory.exceptions.ProduckNameNotExistsInInventory;
import com.inventory.inventory.exceptions.ProductsStockNotAvailableInInInventory;
import com.inventory.inventory.exceptions.SellerNotAvailable;
import com.inventory.inventory.models.Inventory;
import com.inventory.inventory.repository.InventoryRepo;

import com.inventory.inventory.service.kafka.KafkaPublisher;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class InventoryServiceImpl implements InventoryService {

    private InventoryRepo inventoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    private KafkaPublisher kafkaPublisher;
    @Autowired
    public InventoryServiceImpl(InventoryRepo inventoryRepo, ModelMapper modelMapper, KafkaPublisher kafkaPublisher) {
        this.inventoryRepo = inventoryRepo;
        this.modelMapper = modelMapper;
        this.kafkaPublisher = kafkaPublisher;
    }

    @Override
    public ResponseInventoryDto save(RequestInventoryDto requestDto) {
        Inventory inventory =mapToInventory(requestDto);
       Inventory inventory1 = inventoryRepo.save(inventory);
        ResponseInventoryDto responseInventoryDto = modelMapper.map(inventory1, ResponseInventoryDto.class);
        kafkaPublisher.sendMessage("Saved to the Inventory with product Name : "+requestDto.getProductName()+" " +
                " Quantity : "+requestDto.getQuantity());
        return responseInventoryDto;
    }

    private Inventory mapToInventory(RequestInventoryDto requestDto) {
        Inventory inventory = new Inventory();
        inventory.setProductName(requestDto.getProductName());
        inventory.setQuantity(requestDto.getQuantity());
        inventory.setCategory(requestDto.getCategory());
        inventory.setCompany(requestDto.getCompany());
        inventory.setPrice(requestDto.getPrice());
        inventory.setCreatedOn(requestDto.getCreatedOn());
        inventory.setSellerEmailId(requestDto.getSellerEmailId());
        inventory.setUpdatedOn(requestDto.getUpdatedOn());

        return inventory;
    }

    @Override
    public List<ResponseInventoryDto> getAll() {
        List<ResponseInventoryDto> responseInventoryDtos;
        List<Inventory> inventories = inventoryRepo.findAll();
        responseInventoryDtos = Arrays.asList(modelMapper.map(inventories,ResponseInventoryDto[].class));
       kafkaPublisher.sendMessage("Fetched the all list of Inventory details");
        return responseInventoryDtos;
    }

    @Override
    public List<ResponseInventoryDto> getByCategory(String category) {
        List<Inventory> inventories = inventoryRepo.findByCategoryIgnoreCase(category);
        List<ResponseInventoryDto> responseInventoryDtos = Arrays.asList(modelMapper.map(inventories, ResponseInventoryDto[].class));
       kafkaPublisher.sendMessage("Fetched the inventory details by Category : "+category+" ");
        return responseInventoryDtos;
    }

    @Override
    public ResponseInventoryDto getByCompany(String company) {
        Inventory inventories = inventoryRepo.findByCompanyIgnoreCase(company);
        ResponseInventoryDto responseInventoryDtos = modelMapper.map(inventories, ResponseInventoryDto.class);
        kafkaPublisher.sendMessage("Fetched the inventory details by Company Name : "+company+" ");
        return responseInventoryDtos;
    }

    @Override
    public ResponseInventoryDto getByProductName(String productName) {
        Inventory inventories = inventoryRepo.findByProductName(productName);
        ResponseInventoryDto responseInventoryDtos = modelMapper.map(inventories, ResponseInventoryDto.class);
        kafkaPublisher.sendMessage("Fetched the inventory details by Product name : "+productName);
        return responseInventoryDtos;
    }

    @Override
    public ResponseInventoryDto update(UpdateRequest updateRequest) throws ProductsStockNotAvailableInInInventory {
        Inventory inventory= inventoryRepo.findByProductNameAndSellerEmailId(updateRequest.getProductName(),updateRequest.getSellerEmailId());

        int quantities = inventory.getQuantity()+ updateRequest.getQuantity();

        inventory.setQuantity(quantities);
            Inventory inventory1 = inventoryRepo.save(inventory);
            ResponseInventoryDto responseInventoryDto = modelMapper.map(inventory1,ResponseInventoryDto.class);
        kafkaPublisher.sendMessage("Updated the inventory details for selecting quantities : "+ updateRequest.getQuantity());
            return responseInventoryDto;
    }

    @Override
    public List<ResponseInventoryDto> getByProductNameOnlyOne(String productName) throws ProduckNameNotExistsInInventory {
        List<Inventory> inventory = inventoryRepo.findByProductNameOne(productName);
        if(inventory.isEmpty()) {
            throw new ProduckNameNotExistsInInventory("Searched item not found....");
        }
        List<ResponseInventoryDto> responseInventoryDto = Arrays.asList(modelMapper.map(inventory,ResponseInventoryDto[].class));
        kafkaPublisher.sendMessage("Fetched the inventory details for Specific Product : "+productName);
        return responseInventoryDto;
    }

    @Override
    public List<ResponseInventoryDto> getBySellerEmail(String email) throws SellerNotAvailable {

        List<Inventory> inventories = inventoryRepo.findBySellerEmailId(email);
        if(inventories.isEmpty()) {
            throw new SellerNotAvailable("Please check your email....");
        }
        return Arrays.asList(modelMapper.map(inventories,ResponseInventoryDto[].class));
    }

    @Override
    public ResponseInventoryDto getBySellerEmailAndProduct(String email, String product) {
        Inventory inventory = inventoryRepo.findByProductNameAndSellerEmailId(product,email);

        return modelMapper.map(inventory, ResponseInventoryDto.class);
    }
}
