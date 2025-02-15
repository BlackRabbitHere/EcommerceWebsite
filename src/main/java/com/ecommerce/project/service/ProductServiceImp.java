package com.ecommerce.project.service;


import com.ecommerce.project.Repositories.CategoryRepository;
import com.ecommerce.project.Repositories.ProductRepository;
import com.ecommerce.project.exceptions.APIException;
import com.ecommerce.project.exceptions.ResourceNotFoundException;
import com.ecommerce.project.model.Category;
import com.ecommerce.project.model.Product;
import com.ecommerce.project.payload.ProductDTO;
import com.ecommerce.project.payload.ProductResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@Service
public class ProductServiceImp implements ProductService{
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private FileService fileService;

    @Value("${project.image}") // property defined in application.properties
    private String path;

    @Override
    public ProductDTO addProduct(Long categoryId, ProductDTO productDTO){
        //check if product is already present or not
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categotyId",categoryId));

        boolean isProductNotPresent=true;
        List<Product>products=category.getProducts();
        for(int i=0;i<products.size();i++){
            if(products.get(i).getProductName().equals(productDTO.getProductName())){
                isProductNotPresent=false;
                break;
            }
        }

        if(isProductNotPresent){
            Product product = modelMapper.map(productDTO, Product.class);
            product.setImage("default.png");
            product.setCategory(category);
            double specialPrice=product.getPrice()-(product.getDiscount()*0.01)*product.getPrice();
            product.setSpecialPrice(specialPrice);
            Product savedProduct = productRepository.save(product);
            return modelMapper.map(savedProduct, ProductDTO.class);
        }else{
            throw new APIException("product already exists!!");
        }

    }

    @Override
    public ProductResponse getAllProducts() {
        // To check if product size is 0 or not
        List<Product> products=productRepository.findAll();
        List<ProductDTO>productDTOS=products.stream()
                .map(product ->modelMapper.map(product, ProductDTO.class))
                .toList();
        if(products.isEmpty()){
            throw new APIException("No products found!!");
        }
        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchByCategory(Long categoryId) {
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(()-> new ResourceNotFoundException("Category","categotyId",categoryId));

        List<Product> products=productRepository.findByCategoryOrderByPriceAsc(category);
        List<ProductDTO>productDTOS=products.stream()
                .map(product ->modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductResponse searchProductByKeyword(String keyword) {

        List<Product> products=productRepository.findByProductNameLikeIgnoreCase('%'+keyword+'%');
        List<ProductDTO>productDTOS=products.stream()
                .map(product ->modelMapper.map(product, ProductDTO.class))
                .toList();

        ProductResponse productResponse=new ProductResponse();
        productResponse.setContent(productDTOS);
        return productResponse;
    }

    @Override
    public ProductDTO updateProduct(Long productId, ProductDTO productDTO) {
        //Get the existing product
        Product product=modelMapper.map(productDTO, Product.class);
        Product productFromDb=productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

        //update the product info with user productId
        productFromDb.setProductName(product.getProductName());
        productFromDb.setDescription(product.getDescription());
        productFromDb.setQuantity(product.getQuantity());
        productFromDb.setPrice(product.getPrice());
        productFromDb.setDiscount(product.getDiscount());
        double specialPrice=productFromDb.getPrice()-(productFromDb.getDiscount()*0.01)*productFromDb.getPrice();
        productFromDb.setSpecialPrice(specialPrice);

        //save to database
        Product savedProduct=productRepository.save(productFromDb);

        return modelMapper.map(savedProduct,ProductDTO.class);
    }

    @Override
    public ProductDTO deleteProduct(Long productId) {
        Product product=productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));
        productRepository.deleteById(productId);
        return modelMapper.map(product,ProductDTO.class);
    }

    @Override
    public ProductDTO updateProductImage(Long productId, MultipartFile image) throws IOException {
        // Get product from DB
        Product productFromDB=productRepository.findById(productId)
                .orElseThrow(()-> new ResourceNotFoundException("Product","productId",productId));

        // Upload the image to server
        // Get the filename of uploaded image

        String filename= fileService.uploadImage(path,image);

        // Updating the new file name to the product
        productFromDB.setImage(filename);

        //Save updated Product
        Product updatedProduct=productRepository.save(productFromDB);

        // return DTO after mapping product to DTO
        return modelMapper.map(updatedProduct,ProductDTO.class);
    }


}
