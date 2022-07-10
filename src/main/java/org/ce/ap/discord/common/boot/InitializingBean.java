package org.ce.ap.discord.common.boot;

import org.ce.ap.discord.common.entity.boot.ApplicationContext;

/**
 * @author Parham Ahmady
 * @since 6/25/2022
 */
public interface InitializingBean {

    void afterPropertiesSet(ApplicationContext context);
}
