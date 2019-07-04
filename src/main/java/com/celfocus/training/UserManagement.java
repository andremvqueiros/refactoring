package com.celfocus.training;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.celfocus.training.entity.Item;
import com.celfocus.training.entity.ShoppingCart;
import com.celfocus.training.entity.User;
import com.celfocus.training.util.Utils;

public class UserManagement implements ReturnFrontend {

    private static final UserRepository userRepository = new UserRepository();
    private static final int MAX_AGE = 65;

    /**
     * Criar ou atualizar usuario
     *
     * @param name, user name
     * @param birth, user birth date
     */
    public void createUser(String name, String birth) {

        Date birthDate = Utils.toDate(birth, new SimpleDateFormat("dd/mm/yyyy"));

        User user = new User();
        user.setName(name.toUpperCase());
        user.setBirthDate(birthDate);
        user.setOlder(isOlderUser(birthDate));

        userRepository.createOrUpdateUser(user);
    }

    private boolean isOlderUser(Date birthDate) {
        return (new Date().getYear() - birthDate.getYear() > MAX_AGE);
    }

    /**
     * Remover usuario
     *
     * @param name, user name
     */
    public void deleteUser(String name) {
        User user = new User();
        user.setName(name);

        userRepository.deleteUser(user);
    }

    /**
     * Adicionar item ao carrinho
     *
     * @param userName, user name
     * @param itemName, item name
     * @param quantity, quantity
     */
    public void addItemToCart(String userName, String itemName, int quantity) {

        itemName = itemName.toLowerCase().concat("_item");

        userRepository.addItemToShoppingCart(userName, itemName, quantity);
    }

    /**
     * Metodo utilizado para retornar o Usuario no formato do frontend solicitado
     * @param type tipo do frontend utilizado
     * @param user usuario que será renderizado
     * @return o texto no formato solicitado com as informarções do user
     */
    @Override
    public String returnFrontendUser(String type, User user) {
        if (type.equals("html")) {
            return "<div>"
                    + "<h1>User</h1>"
                    + "<span>" + user.getName() + "</span>"
                    + "<span>" + user.getBirthDate() + "</span>"
                    + "<span>" + user.isOlder() + "</span>"
                    + "</div>";
        } else if (type.equals("xml")) {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>"
                    + "<name> " + user.getName() + "</name>"
                    + "<bd>" + user.getBirthDate() + "</bd>"
                    + "<older> " + user.isOlder() + "</older>";
        }

        return "";
    }

    /**
     * Metodo utilizado para retornar o Shoppingcart no formato do frontend solicitado
     * @param type tipo do frontend utilizado
     * @param shoppingCart shoppingCart que será renderizado
     * @return o texto no formato solicitado com as informarções do shoppingCart
     */
    @Override
    public String returnFrontendShoppingCart(String type, ShoppingCart shoppingCart) {
        if (type.equals("html")) {
            return "<div>"
                    + "<h1>ShoppingCart</h1>"
                    + "<span> " + shoppingCart.getUser() + "</span>"
                    + "<span> " + shoppingCart.getItens() + "</span>"
                    + "</div>";
        } else if (type.equals("xml")) {
            return "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\" ?>"
                    + "<user> " + shoppingCart.getUser() + "</user>"
                    + "<itens> " + shoppingCart.getItens() + "</itens>";
        }

        return "";
    }

    /**
     * Metodo utilizado para retornar o Item no formato do frontend solicitado
     * @param type tipo do frontend utilizado
     * @param item item que será renderizado
     * @return o texto no formato solicitado com as informarções do item
     */
    @Override
    public String returnFrontendItem(String type, Item item) {
        if (type.equals("html")) {
            return "<div>"
                    + "<h1>Item</h1>"
                    + "<span> " + item.getName() + "</span>"
                    + "<span> " + item.getValue() + "</span>"
                    + "</div>";
        } else if (type.equals("xml")) {
            return "<name> " + item.getName() + "</name>"
                    + "<valor> " + item.getValue() + "</valor>";
        }

        return "";
    }
}