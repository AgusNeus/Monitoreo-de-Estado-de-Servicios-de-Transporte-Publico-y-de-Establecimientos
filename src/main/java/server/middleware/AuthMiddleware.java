package server.middleware;

        import domain.entidades.signin.RolUsuario;
        import io.javalin.config.JavalinConfig;
        import io.javalin.http.Context;
        import server.exceptions.AccessDeniedException;

public class AuthMiddleware {

    public static void apply(JavalinConfig config) {
        config.accessManager(((handler, context, routeRoles) -> {
            RolUsuario userRole = getUserRoleType(context);

            if (routeRoles.size() == 0 || routeRoles.contains(userRole)) {
                handler.handle(context);
            } else {
                throw new AccessDeniedException();
            }
        }));


    }

    public static RolUsuario getUserRoleType(Context context) {
        return context.sessionAttribute("tipo_rol");
    }
}