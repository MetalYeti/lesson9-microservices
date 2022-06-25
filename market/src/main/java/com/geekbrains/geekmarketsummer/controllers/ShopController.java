package com.geekbrains.geekmarketsummer.controllers;

import contract.entities.Order;
import contract.entities.User;
import com.geekbrains.geekmarketsummer.services.*;
import contract.entities.ShoppingCart;
import contract.entities.Product;
import contract.specifications.ProductSpecs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
@RequestMapping("/shop")
public class ShopController {
    private static final int INITIAL_PAGE = 0;
    private static final int PAGE_SIZE = 5;

    private MailService mailService;
    private UserService userService;
    private OrderService orderService;

    private ShoppingCartService shoppingCartService;
    private DeliveryAddressService deliverAddressService;
    private SimpMessagingTemplate template;
    private ProductService productClient;

    private Logger logger = LoggerFactory.getLogger(ShopController.class);

    @Autowired
    public void setMailService(MailService mailService) {
        this.mailService = mailService;
    }

    @Autowired
    public void setProductClient(ProductService productClient) {
        this.productClient = productClient;
    }

    @Autowired
    public void setShoppingCartService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setDeliverAddressService(DeliveryAddressService deliverAddressService) {
        this.deliverAddressService = deliverAddressService;
    }

    @Autowired
    public void setTemplate(SimpMessagingTemplate template) {
        this.template = template;
    }

    @GetMapping
    public String shopPage(Model model,
                           @RequestParam(value = "page") Optional<Integer> page,
                           @RequestParam(value = "word", required = false) String word,
                           @RequestParam(value = "min", required = false) Double min,
                           @RequestParam(value = "max", required = false) Double max
    ) {
        final int currentPage = (page.orElse(0) < 1) ? INITIAL_PAGE : page.get() - 1;

        Specification<Product> spec = Specification.where(null);
        StringBuilder filters = new StringBuilder();
        if (word != null) {
            spec = spec.and(ProductSpecs.titleContains(word));
            filters.append("&word=" + word);
        }
        if (min != null) {
            spec = spec.and(ProductSpecs.priceGreaterThanOrEq(min));
            filters.append("&min=" + min);
        }
        if (max != null) {
            spec = spec.and(ProductSpecs.priceLesserThanOrEq(max));
            filters.append("&max=" + max);
        }

        Page<Product> productsList = productClient.getProductsWithPagingAndFiltering(currentPage, PAGE_SIZE, word, min, max);

        model.addAttribute("products", productsList.getContent());
        model.addAttribute("page", currentPage);
        model.addAttribute("totalPage", productsList.getTotalPages());

        model.addAttribute("filters", filters.toString());

        model.addAttribute("min", min);
        model.addAttribute("max", max);
        model.addAttribute("word", word);


        return "shop-page";
    }

    @GetMapping("/cart/add/{id}")
    public String addProductToCart(Model model, @PathVariable("id") Long id, HttpServletRequest httpServletRequest) {
        shoppingCartService.addToCart(httpServletRequest.getSession(), id);
        String referrer = httpServletRequest.getHeader("referer");
        return "redirect:" + referrer;
    }


    @GetMapping("/order/fill")
    public String orderFill(HttpSession httpSession, Model model, Principal principal) {
        User user = userService.findByUserName(principal.getName());
        ShoppingCart cart = shoppingCartService.getCurrentCart(httpSession);
        model.addAttribute("cart", cart);
        model.addAttribute("order", new Order());
        model.addAttribute("addresses", deliverAddressService.getUserAddresses(user.getId()));
        return "order-form";
    }


    @PostMapping("/order/confirm")
    public String orderConfirm(Model model, HttpServletRequest httpServletRequest, @ModelAttribute(name = "order") Order orderFromFrontend, Principal principal) {
        if (principal == null) {
            return "redirect:/login";
        }
        //logger.info(orderFromFrontend.toString());
//        User user = userService.findByUserName(principal.getName());
        Order order = orderService.makeOrder(shoppingCartService.getCurrentCart(httpServletRequest.getSession()), principal.getName());
        order.setDeliveryAddress(orderFromFrontend.getDeliveryAddress());
        order.setPhoneNumber(orderFromFrontend.getPhoneNumber());
        order.setDeliveryDate(LocalDateTime.now().plusDays(7));
        order.setDeliveryPrice(BigDecimal.ZERO);
        order = orderService.saveOrder(order);
        model.addAttribute("order", order);
        return "order-filler";
    }

    @GetMapping("/order/result/{id}")
    public String orderConfirm(HttpSession httpSession, Model model, @PathVariable(name = "id") Long id, Principal principal) throws MessagingException {
        if (principal == null) {
            return "redirect:/login";
        }
        // todo ждем до оплаты, проверка безопасности и проблема с повторной отправкой письма сделать одноразовый вход
        User user = userService.findByUserName(principal.getName());
        Order confirmedOrder = orderService.findById(id);
        if (!user.getId().equals(confirmedOrder.getUser().getId())) {
            return "redirect:/";
        }
        mailService.sendOrderNotification(confirmedOrder);
        shoppingCartService.resetCart(httpSession);
        model.addAttribute("order", confirmedOrder);
        return "order-result";
    }
}
