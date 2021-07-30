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
    boolean postText(String text);

    /**
     * Sending a text message with replay keyboard
     *
     * @param text text of messageForUser
     * @return true if messageForUser received successful
     */
    boolean postText(String text, ViberKeyboard keyboard);

    boolean postKeyboard(ViberKeyboard keyboard);

    boolean postPicture(String pictureUrl, String description);

    boolean postPicture(String pictureUrl, ViberKeyboard viberKeyboard);

    boolean postPicture(String pictureUrl, String description, String thumbnailUrl);

    boolean postVideo(String videoUrl, Integer size);

    boolean postVideo(String videoUrl, Integer size, Integer duration, String thumbnailUrl);

    boolean postFile(String fileUrl, Integer size, String fileName);

    boolean postContact(String contactName, String phone);

    boolean postLocation(Float latitude, Float longitude);

    boolean postUrl(String url);

    boolean postUrl(String url, ViberKeyboard keyboard);

    boolean postSticker(Integer stickerId);

    boolean postCarousel(RichMedia richMedia);

    boolean postCarousel(RichMedia richMedia, ViberKeyboard keyboard);
}
