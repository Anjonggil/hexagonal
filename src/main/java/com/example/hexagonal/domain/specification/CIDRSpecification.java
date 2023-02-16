package com.example.hexagonal.domain.specification;

import com.example.hexagonal.domain.specification.share.AbstractSpecification;

public class CIDRSpecification extends AbstractSpecification<Integer> {

    final static public int MINIMUM_ALLOWED_CIDR = 8;

    @Override
    public boolean isSatisfiedBy(Integer cidr) {
        return cidr > MINIMUM_ALLOWED_CIDR;
    }
}
