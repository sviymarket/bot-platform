package com.reconsale.test.bot.response;

import com.reconsale.bot.integration.AbstractResponseCase;
import com.reconsale.bot.model.Response;

import java.util.Objects;

public class PlusResponseCase extends AbstractResponseCase {
    @Override
    public Object provideResponse(Response response) {
        //return Response.builder().;
        return null;
    }

    @Override
    public boolean evaluate(Response response) {
        return Objects.nonNull(response) && "plus".equals(response.getReference());
    }
}
