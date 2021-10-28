package com.reconsale.bot.integration.viber.outbound;

import com.reconsale.bot.model.viber.output.RichMedia;
import com.reconsale.bot.model.viber.output.keyboard.ViberKeyboard;

public interface Outgoing {

    /**
     * Sending a text message
     *
     * @param text text of messageForUser
     * @return true if messageForUser received successful
     */
    String postText(String text);

    /**
     * Sending a text message with replay keyboard
     *
     * @param text text of messageForUser
     * @return true if messageForUser received successful
     */
    String postText(String text, ViberKeyboard keyboard);

    String postKeyboard(ViberKeyboard keyboard);

    String postPicture(String pictureUrl, String description);

    String postPicture(String pictureUrl, ViberKeyboard viberKeyboard);

    String postPicture(String pictureUrl, String description, ViberKeyboard viberKeyboard);

    String postPicture(String pictureUrl, String description, String thumbnailUrl);

    String postVideo(String videoUrl, Integer size);

    String postVideo(String videoUrl, Integer size, Integer duration, String thumbnailUrl);

    String postFile(String fileUrl, Integer size, String fileName);

    String postContact(String contactName, String phone);

    String postLocation(Float latitude, Float longitude);

    String postUrl(String url);

    String postUrl(String url, ViberKeyboard keyboard);

    String postSticker(Integer stickerId);

    String postCarousel(RichMedia richMedia);

    String postCarousel(RichMedia richMedia, ViberKeyboard keyboard);
}
