package com.rined.smalltalk.dto;

public final class Views {

    public interface View {}

    public interface Id extends View {}

    public interface IdName extends Id {}

    public interface FullComment extends IdName {}

    public interface FullMessage extends IdName {}

    public interface FullProfile extends IdName{}
}
