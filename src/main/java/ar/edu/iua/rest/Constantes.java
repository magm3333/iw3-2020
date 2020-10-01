package ar.edu.iua.rest;

public final class Constantes {

	public static final String URL_API = "/api";
	public static final String URL_API_VERSION = "/v1";
	public static final String URL_BASE = URL_API + URL_API_VERSION;

	public static final String URL_PRODUCTOS = URL_BASE + "/productos";

	public static final String URL_USERS = URL_BASE + "/users";

	public static final String URL_AUTH_INFO = "/auth-info";
	public static final String URL_LOGOUT = "/logout-token";

	public static final String URL_AUTH = URL_BASE + "/auth";

	public static final String URL_WEBSOCKET_ENPOINT = URL_BASE + "/ws";

	public static final String TOPIC_SEND_WEBSOCKET_GRAPH = "/iw3/data";

	public static final String URL_GRAPH = URL_BASE + "/graph";

}
