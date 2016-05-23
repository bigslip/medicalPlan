package ir.parsdeveloper.commons.listener;

/**
 * @author hadi tayebi
 */
public class PersistEntityListener {//implements PreInsertEventListener, PostInsertEventListener, Serializable {
    /*protected Logger logger = LoggerFactory.getLogger(this.getClass());


    @Override
    public void onPostInsert(PostInsertEvent event) {
        logger.info(event.getId().toString());
        event.getEntity();
    }


    public boolean requiresPostCommitHanding(EntityPersister entityPersister) {
        return false;
    }


    @Override
    public boolean onPreInsert(PreInsertEvent event) {
        BaseModel entity = (BaseModel) event.getEntity();
        Class<? extends BaseModel> clazz = entity.getClass();
        Method[] clazzMethods = clazz.getDeclaredMethods();
        for (Method method : clazzMethods) {
            ManyToOne manyToOne = method.getAnnotation(ManyToOne.class);
            if (manyToOne != null) {
                try {
                    BaseModel obj = (BaseModel) method.invoke(entity);
                    if (obj != null && (obj.getId()).longValue() == -1) {
                        String methodName = method.getName();
                        methodName = methodName.replaceFirst("g", "s");
                        clazz.getMethod(methodName, method.getReturnType()).invoke(entity, new Object[]{null});
                    }
                } catch (Exception e) {
                    return Boolean.FALSE;
                }
            }
        }
        return Boolean.TRUE;
    }

    @PrePersist
    void prePersist(Object object) {

    }
*/
}
