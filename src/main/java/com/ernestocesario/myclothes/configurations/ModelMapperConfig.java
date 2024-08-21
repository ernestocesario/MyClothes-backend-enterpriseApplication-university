package com.ernestocesario.myclothes.configurations;

import com.ernestocesario.myclothes.persistance.DTOs.*;
import com.ernestocesario.myclothes.persistance.entities.*;
import com.ernestocesario.myclothes.persistance.entities.utils.ProductSnapshot;
import lombok.RequiredArgsConstructor;
import org.modelmapper.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setSkipNullEnabled(true);

        PropertyMap<User, AuthResponseDTO> userAuthTokenDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setAccessToken(source.getAccessToken());
                map().setRefreshToken(source.getRefreshToken());
            }
        };

        PropertyMap<Cart, CartDTO> cartCartDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map(source.getCartElements(), destination.getProducts());
            }
        };

        PropertyMap<Chat, ChatDTO> chatChatDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setActive(source.isActive());
                map().setCreationTime(source.getCreationTime());
                map().setClosingTime(source.getClosingTime());
                map().setUserEmail(source.getCustomer().getEmail());
            }
        };

        PropertyMap<Customer, CustomerProfileDTO> customerCustomerProfileDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setUsername(source.getUsername());
                map().setEmail(source.getEmail());
                map().setImageUrl(source.getImageUrl());
                map().setBalance(source.getBalance());
                map().setBanned(source.isBanned());
                map().setShippingInfo(source.getShippingInfo());
            }
        };

        PropertyMap<DiscountCode, DiscountCodeDTO> discountCodeDiscountCodeDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setCode(source.getCode());
                map().setDiscountPercentage(source.getDiscountPercentage());
                map().setUsed(source.isUsed());
            }
        };

        PropertyMap<Chat, FullChatDTO> chatFullChatDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                using(ctx -> modelMapper.map(ctx.getSource(), ChatDTO.class))
                        .map(source, destination);

                map(source.getMessages(), destination.getMessages());
            }
        };

        PropertyMap<Order, FullOrderDTO> orderFullOrderDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                using(ctx -> modelMapper.map(ctx.getSource(), OrderDTO.class))
                        .map(source, destination);

                map().setShippingInfo(source.getShippingInfo());
                map(source.getOrderProducts(), destination.getProducts());
            }
        };

        PropertyMap<Wishlist, FullWishlistDTO> wishlistFullWishlistDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                using(ctx -> modelMapper.map(ctx.getSource(), WishlistDTO.class))
                        .map(source, destination);

                map(source.getProductVariants(), destination.getProducts());
            }
        };

        PropertyMap<Message, MessageDTO> messageMessageDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setContent(source.getContent());
                map().setCreationTime(source.getCreationTime());
                map().setFromAdmin(source.getAdmin() != null);
            }
        };

        PropertyMap<Order, OrderDTO> orderOrderDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setOrderDate(source.getOrderDate());
                map().setSubtotalPrice(source.getSubtotalPrice());
                map().setDiscount(source.getDiscount());
                map().setTotalPrice(source.getTotalPrice());
                map().setOrderStatus(source.getOrderStatus());
            }
        };

        PropertyMap<ProductVariant, ProductDTO> productVariantProductDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setProductId(source.getProduct().getId());
                map().setProductVariantId(source.getId());
                map().setBrand(source.getProduct().getBrand());
                map().setName(source.getProduct().getName());
                map().setDescription(source.getProduct().getDescription());
                map().setCategory(source.getProduct().getCategory());
                map().setGender(source.getGender());
                map().setStyle(source.getStyle());
                map().setSize(source.getSize());
                map().setPrice(source.getPrice());
            }
        };

        PropertyMap<ProductSnapshot, ProductDTO> productSnapshotProductDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setProductId(null);
                map().setProductVariantId(null);
                map().setBrand(source.getBrand());
                map().setName(source.getName());
                map().setDescription(source.getDescription());
                map().setCategory(source.getCategory());
                map().setGender(source.getGender());
                map().setStyle(source.getStyle());
                map().setSize(source.getSize());
                map().setPrice(source.getPrice());
            }
        };

        PropertyMap<CartElement, ProductRequestDTO> productRequestProductRequestDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setQuantity(source.getQuantity());
                map(source.getProductVariant(), destination.getProductDTO());
            }
        };

        PropertyMap<OrderProduct, ProductRequestDTO> orderProductProductRequestDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setQuantity(source.getQuantity());
                map(source.getProductSnapshot(), destination.getProductDTO());
            }
        };

        PropertyMap<Review, ReviewDTO> reviewReviewDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setTitle(source.getTitle());
                map().setContent(source.getContent());
                map().setStars(source.getStars());
                map().setUsername(source.getCustomer().getUsername());
                map(source.getProductVariant(), destination.getProductDTO());
            }
        };

        PropertyMap<User, UserProfileDTO> userUserProfileDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setUsername(source.getUsername());
                map().setEmail(source.getEmail());
                map().setImageUrl(source.getImageUrl());
            }
        };

        PropertyMap<Wishlist, WishlistDTO> wishlistWishlistDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setName(source.getName());
            }
        };

        PropertyMap<WishlistAccess, WishlistSharingDTO> wishlistAccessWishlistSharingDTOPropertyMap = new PropertyMap<>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setUserEmail(source.getCustomer().getEmail());
            }
        };


        modelMapper.addMappings(userAuthTokenDTOPropertyMap);
        modelMapper.addMappings(cartCartDTOPropertyMap);
        modelMapper.addMappings(chatChatDTOPropertyMap);
        modelMapper.addMappings(customerCustomerProfileDTOPropertyMap);
        modelMapper.addMappings(discountCodeDiscountCodeDTOPropertyMap);
        modelMapper.addMappings(chatFullChatDTOPropertyMap);
        modelMapper.addMappings(orderFullOrderDTOPropertyMap);
        modelMapper.addMappings(wishlistFullWishlistDTOPropertyMap);
        modelMapper.addMappings(messageMessageDTOPropertyMap);
        modelMapper.addMappings(orderOrderDTOPropertyMap);
        modelMapper.addMappings(productVariantProductDTOPropertyMap);
        modelMapper.addMappings(productSnapshotProductDTOPropertyMap);
        modelMapper.addMappings(productRequestProductRequestDTOPropertyMap);
        modelMapper.addMappings(orderProductProductRequestDTOPropertyMap);
        modelMapper.addMappings(reviewReviewDTOPropertyMap);
        modelMapper.addMappings(userUserProfileDTOPropertyMap);
        modelMapper.addMappings(wishlistWishlistDTOPropertyMap);
        modelMapper.addMappings(wishlistAccessWishlistSharingDTOPropertyMap);


        return modelMapper;
    }
}
