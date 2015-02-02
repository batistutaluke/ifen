package me.ifen.api.ifen.services;

/**
 * Created by zhangjingbo on 15/2/1.
 */
public interface SupportService {

    public void support(Long id, Long creatorId);

    public void delete(Long id, Long creatorId);

    public long count(Long id);
}
