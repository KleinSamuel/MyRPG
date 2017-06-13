package com.kleinsamuel.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.kleinsamuel.game.model.Assets;
import com.kleinsamuel.game.model.data.UserContent;
import com.kleinsamuel.game.model.entities.OtherPlayer;
import com.kleinsamuel.game.model.entities.Player;
import com.kleinsamuel.game.model.entities.npcs.NPC;
import com.kleinsamuel.game.model.items.Item;
import com.kleinsamuel.game.model.maps.MapFactory;
import com.kleinsamuel.game.screens.PlayScreen;
import com.kleinsamuel.game.sprites.SpriteSheet;
import com.kleinsamuel.game.startscreen.StartScreen;
import com.kleinsamuel.game.util.DebugMessageFactory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

import io.socket.client.IO;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class GameClass extends Game {

	private String serverName = "87.160.50.83";
	private String port = "8081";
	private IO.Options socketOptions;

	public String clientID;

	public boolean IS_FIRST_STARTUP = true;

	private final float UPDATE_TIMER = 1/40f;
	private float timer;

    public boolean CONNECTED = false;
	public boolean signInSuccessfull = false;

	public boolean LOG_IN_ANSWER = false;
	public boolean LOG_IN_SUCCESS = false;

	private Socket socket;
	public StartScreen startScreen;
	public PlayScreen playScreen;
	public GameClass game;

	public Music main_menu_music;
	public Sound button_click;

	public ConcurrentHashMap<String, OtherPlayer> otherPlayers;
	public ConcurrentHashMap<Integer, NPC> npcs;
	public CopyOnWriteArrayList<Item> items;

	public String userName;

	@Override
	public void create() {
		DebugMessageFactory.printNormalMessage("STARTED GAME INSTANCE.");

		socketOptions = new IO.Options();
		socketOptions.timeout = 5000;
		socketOptions.reconnectionAttempts = 0;

		Assets.load();
		Assets.manager.finishLoading();
		Assets.manager.update();

		game = this;

		otherPlayers = new ConcurrentHashMap<String, OtherPlayer>();
		npcs = new ConcurrentHashMap<Integer, NPC>();
		items = new CopyOnWriteArrayList<Item>();

		startScreen = new StartScreen(this);

		main_menu_music = Gdx.audio.newMusic(Gdx.files.internal("music/main_menu_music.mp3"));
		main_menu_music.setLooping(true);

		button_click = Gdx.audio.newSound(Gdx.files.internal("sounds/button_click_01.wav"));

		connect();

		if(UserContent.readFromFile().ID != -1){
			IS_FIRST_STARTUP = false;
			DebugMessageFactory.printInfoMessage("GAMECLASS: NOT FIRST STARTUP");
		}else{
			IS_FIRST_STARTUP = true;
			DebugMessageFactory.printInfoMessage("GAMECLASS: FIRST STARTUP");
		}

		/* check if it is first startup of app, if yes start in main menu if no start in game */
		if(IS_FIRST_STARTUP){
			main_menu_music.play();
			setScreen(startScreen);
		}else if(CONNECTED){
			setScreen(new PlayScreen(this));
		}else{
			main_menu_music.play();
			setScreen(startScreen);
		}
	}

	public void connect(){
		connectSocket();
		configSocketEvents();
	}

	public void logInUser(String username, String password){
		JSONObject data = new JSONObject();

		try {
			data.put("username", username);
			data.put("password", password);

			socket.emit("logInUser", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("ERROR SENDING LOG IN DATA!");
		}
	}

	public void registerPlayer(String username, String password){

		signInSuccessfull = false;

		JSONObject data = new JSONObject();

		try {
			data.put("username", username);
			data.put("password", password);

			socket.emit("registerNewUser", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("ERROR SENDING REGISTERING DATA!");
		}
	}

	public void updateServer_Position(float delta, Player player){
		timer += delta;

		if(timer >= UPDATE_TIMER && playScreen != null){
			JSONObject data = new JSONObject();
			try {
				data.put("NAME", player.content.NAME);
				data.put("x", player.content.x);
				data.put("y", player.content.y);
				data.put("LEVEL", player.content.LEVEL);

				socket.emit("playerMoved", data);

			} catch (JSONException e){
				DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING UPDATE DATA");
			}
		}
	}

	public void playerMovedPoint(Player p){
		JSONObject data = new JSONObject();
		try {
			data.put("x", p.moveTo.x);
			data.put("y", p.moveTo.y);
			data.put("currentX", p.content.x);
			data.put("currentY", p.content.y);

			socket.emit("playerMovedPoint", data);

		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING PLAYER MOVED POINT DATA");
		}
	}

	public void playerMovedToAnotherSection(Player p){

		otherPlayers.clear();
		npcs.clear();

		JSONObject data = new JSONObject();
		try {
			data.put("mapIdentifier", MapFactory.parseIdentifierFromDetailToSmall(p.content.mapIdentifier));
			data.put("x", p.content.x);
			data.put("y", p.content.y);

			DebugMessageFactory.printInfoMessage("SEND UPDATE FOR SECTION CHANGE");

			socket.emit("playerMovedToAnotherSection", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("ERROR SENDING PLAYER MOVED TO ANOTHER SECTION UPDATE");
		}
	}

	public void updateServer_Health(Player p){
		JSONObject data = new JSONObject();

		try {
			data.put("currHealth", p.content.CURRENT_HEALTH);
			data.put("maxHealth", p.content.MAX_HEALTH);

			socket.emit("healthUpdate", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING HEALTH UPDATE");
		}
	}

	public void updateServer_Mana(Player p){
		JSONObject data = new JSONObject();

		try {
			data.put("currMana", p.content.CURRENT_MANA);
			data.put("maxMana", p.content.MAX_MANA);

			socket.emit("manaUpdate", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING MANA UPDATE");
		}
	}

	public void updateServer_Level(Player p){
		JSONObject data = new JSONObject();

		try {
			data.put("LEVEL", p.content.LEVEL);

			socket.emit("levelUpdate", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING LEVEL UPDATE");
		}
	}

	public void damageNpc(int npcId, int amount){
		JSONObject data = new JSONObject();
		try {
			data.put("npcId", npcId);
			data.put("amount", amount);
			socket.emit("damageToNpc", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING DAMAGE TO NPC UPDATE");
		}
	}

	public void killNpc(int npcId){
		JSONObject data = new JSONObject();
		try {
			data.put("npcId", npcId);
			socket.emit("killNpc", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING KILL NPC UPDATE");
		}
	}

	public void itemPicked(Item item){
		JSONObject data = new JSONObject();
		try {
			data.put("item_key", item.data.getItem_key());
			data.put("x", item.data.getX());
			data.put("y", item.data.getY());

			socket.emit("itemPicked", data);
		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING ITEM PICKED UPDATE");
		}
	}

	public void sendInitialInfo(Player p){
		JSONObject data = new JSONObject();

		try {
			data.put("name", p.content.NAME);
			data.put("x", p.content.x);
			data.put("y", p.content.y);
			data.put("mapIdentifier", MapFactory.parseIdentifierFromDetailToSmall(p.content.mapIdentifier));
			data.put("level", p.content.LEVEL);
			data.put("currHealth", p.content.CURRENT_HEALTH);
			data.put("maxHealth", p.content.MAX_HEALTH);
			data.put("currMana", p.content.CURRENT_MANA);
			data.put("maxMana", p.content.MAX_MANA);

			socket.emit("initPlayer", data);

			DebugMessageFactory.printInfoMessage("SENT INITIAL PLAYER DATA");

		} catch (JSONException e){
			DebugMessageFactory.printErrorMessage("SOCKET:IO ERROR SENDING INITIAL INFO");
		}
	}

	public void connectSocket(){

		if(socket != null){
			socket.disconnect();
			socket.close();
		}

		DebugMessageFactory.printNormalMessage("CONNECTING TO SERVER ["+serverName+":"+port+"]...");
		try {
			socket = null;
			socket = IO.socket("http://"+serverName+":"+port+"/player", socketOptions);
			socket.connect();
		} catch( Exception e){
			DebugMessageFactory.printNormalMessage("COULD NOT CONNECT TO SERVER!");
			e.printStackTrace();
			System.exit(1);
		}
	}

	public void configSocketEvents(){
		socket.on(Socket.EVENT_CONNECT, new Emitter.Listener(){
			@Override
			public void call(Object... args) {
				DebugMessageFactory.printInfoMessage("Socket IO Connected!");
				CONNECTED = true;
			}
		}).on(Socket.EVENT_DISCONNECT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				CONNECTED = false;
				DebugMessageFactory.printInfoMessage("SET START SCREEN!");
				if(playScreen != null) playScreen.hide();
				if(!main_menu_music.isPlaying()){
					main_menu_music.play();
				}
				setScreen(startScreen);
			}
		}).on(Socket.EVENT_CONNECT_TIMEOUT, new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				DebugMessageFactory.printInfoMessage("TIMEOUT");
			}
		}).on("socketID", new Emitter.Listener(){
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					clientID = id;
					DebugMessageFactory.printInfoMessage("Received ID: ["+id+"]");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		}).on("logInUserAnswer", new Emitter.Listener(){
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					boolean status = data.getBoolean("status");
					LOG_IN_ANSWER = true;
					LOG_IN_SUCCESS = status;
					DebugMessageFactory.printInfoMessage("Received STATUS: ["+status+"]");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		socket.on("initNewPlayer", new Emitter.Listener(){
            @Override
            public void call(Object... args) {
                JSONObject data = (JSONObject) args[0];
                try {

                    String id = data.getString("id");
                    String name = data.getString("name");
					int level = data.getInt("level");
					String mapIdentifier = MapFactory.parseIdentifierFromSmallToDetail(data.getString("mapIdentifier"));
                    int entityX = data.getInt("x");
                    int entityY = data.getInt("y");
                    int currentHealth = data.getInt("currHealth");
                    int maxHealth = data.getInt("maxHealth");

                    if(playScreen != null) {
						if (mapIdentifier.equals(playScreen.player.content.mapIdentifier)) {
							playScreen.addOtherPlayer(id, name, level, entityX, entityY, currentHealth, maxHealth);
							DebugMessageFactory.printInfoMessage("INIT NEW PLAYER IN SAME SECTION");
						}
					}
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
		socket.on("playerDisconnected", new Emitter.Listener(){
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					DebugMessageFactory.printInfoMessage("Player disconnected: ["+id+"]");

					if(playScreen != null) {
						playScreen.removeOtherPlayer(id);
						DebugMessageFactory.printInfoMessage("REMOVE DISCONNECTED PLAYER");
					}

					DebugMessageFactory.printInfoMessage("OtherPlayers Size: "+otherPlayers.size());
					for(Map.Entry<String, OtherPlayer> entry : otherPlayers.entrySet()){
						DebugMessageFactory.printInfoMessage("KEY: "+entry.getKey());
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
		socket.on("getPlayers", new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				JSONArray objects = (JSONArray) args[0];
				try {

					for(int i = 0; i < objects.length(); i++){

						String id = objects.getJSONObject(i).getString("id");

                        if(id.equals(clientID)){
                            continue;
                        }

						String name = objects.getJSONObject(i).getString("name");
                        int level = objects.getJSONObject(i).getInt("level");
						String mapIdentifier = MapFactory.parseIdentifierFromSmallToDetail(objects.getJSONObject(i).getString("mapIdentifier"));
						int entityX = objects.getJSONObject(i).getInt("x");
						int entityY = objects.getJSONObject(i).getInt("y");
                        int currentHealth = objects.getJSONObject(i).getInt("currHealth");
                        int maxHealth = objects.getJSONObject(i).getInt("maxHealth");

						if(playScreen != null) {
							if(mapIdentifier.equals(playScreen.player.content.mapIdentifier)) {
								playScreen.addOtherPlayer(id, name, level, entityX, entityY, currentHealth, maxHealth);
							}
						}
					}
				} catch(JSONException e){
					DebugMessageFactory.printErrorMessage("ERROR GETTING OTHER PLAYERS!");
					e.printStackTrace();
				}
			}
		}).on("playerMoved", new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					String name = data.getString("name");
					int entityX = data.getInt("x");
					int entityY = data.getInt("y");

					if(playScreen != null) {
                        playScreen.updateOtherPlayer(id, name, entityX, entityY);
					}
				} catch(JSONException e){
                    DebugMessageFactory.printErrorMessage("ERROR UPDATING OTHER PLAYERS!");
				}
			}
		}).on("playerMovedPoint", new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				JSONObject data = (JSONObject) args[0];
				try {
					String id = data.getString("id");
					int entityX = data.getInt("x");
					int entityY = data.getInt("y");

					if(playScreen != null) {
						playScreen.setOtherPlayerPoint(id, entityX, entityY);
					}
				} catch(JSONException e){
                    DebugMessageFactory.printErrorMessage("ERROR GETTING OTHER PLAYERS POINT!");
				}
			}
		});

		socket.on("playerMovedToAnotherSection", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {

					String id = data.getString("id");
					String name = data.getString("name");
                    int level = data.getInt("level");
					String mapIdentifier = MapFactory.parseIdentifierFromSmallToDetail(data.getString("mapIdentifier"));
					int entityX = data.getInt("x");
					int entityY = data.getInt("y");
                    int currentHealth = data.getInt("currHealth");
                    int maxHealth = data.getInt("maxHealth");

					if(playScreen != null){
						if(mapIdentifier.equals(playScreen.player.content.mapIdentifier)) {
							playScreen.addOtherPlayer(id, name, level, entityX, entityY, currentHealth, maxHealth);
						}else{
							playScreen.removeOtherPlayer(id);
						}
					}

				} catch(JSONException e){

				}
			}
		});

		socket.on("registerNewUserAnswer", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					boolean success = data.getBoolean("status");
					signInSuccessfull = success;
				} catch(JSONException e){

				}
				startScreen.signUpScreen.changeStatusText = false;

			}
		}).on("getNPCs", new Emitter.Listener() {
			@Override
			public void call(Object... args) {

				JSONArray objects = (JSONArray) args[0];

				try {

					for(int i = 0; i < objects.length(); i++) {

						int id = objects.getJSONObject(i).getInt("id");
						int npc_key = objects.getJSONObject(i).getInt("npc_key");
						int level = objects.getJSONObject(i).getInt("level");
						float x = objects.getJSONObject(i).getInt("x");
						float y = objects.getJSONObject(i).getInt("y");
						String mapIdentifier = objects.getJSONObject(i).getString("mapIdentifier");
						float speed = (float)objects.getJSONObject(i).getDouble("speed");
						int current_health = objects.getJSONObject(i).getInt("currHealth");
						int max_health = objects.getJSONObject(i).getInt("maxHealth");

                        if(playScreen != null) {
							playScreen.addNPC(id, npc_key, level, x, y, mapIdentifier, speed, current_health, max_health);
                        }
					}

				} catch(JSONException e){
					DebugMessageFactory.printErrorMessage("ERROR HANDLING GET NPCS");
					e.printStackTrace();
				}
			}
		}).on("npcMoved", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					int id = data.getInt("id");
                    float x = data.getInt("x");
                    float y = data.getInt("y");

                    if(playScreen != null) {
                        playScreen.updateNPCPosition(id, x, y);
                    }

				} catch(JSONException e){
                    DebugMessageFactory.printErrorMessage("ERROR UPDATING NPC MOVED");
				}
			}
		}).on("npcMovedPoint", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					int id = data.getInt("id");
					float x = data.getInt("pointX");
					float y = data.getInt("pointY");

					if(playScreen != null) {
						playScreen.updateNPCPoint(id, x, y);
					}

				} catch(JSONException e){
					DebugMessageFactory.printErrorMessage("ERROR UPDATING NPC MOVED POINT");
				}
			}
		});
		socket.on("newNpc", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {

					int id = data.getInt("id");
					int npc_key = data.getInt("npc_key");
					int level = data.getInt("level");
					int x = data.getInt("x");
					int y = data.getInt("y");
					String mapIdentifier = data.getString("mapIdentifier");
					double speed = data.getDouble("speed");
					int current_health = data.getInt("current_health");
					int max_health = data.getInt("max_health");

					if(playScreen != null) {
						playScreen.addNPC(id, npc_key, level, x, y, mapIdentifier, (float)speed, current_health, max_health);
					}

				} catch(JSONException e){
					DebugMessageFactory.printErrorMessage("ERROR ADDING NEW NPC");
				}
			}
		});
		socket.on("damageToNpc", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					String fromId = data.getString("from");
					int toId = data.getInt("to");
					int amount = data.getInt("amount");

					if(playScreen != null) {
						playScreen.damageNpc(fromId, toId, amount, false);
					}

				} catch(JSONException e){
					DebugMessageFactory.printErrorMessage("ERROR UPDATING DAMAGE TO NPCS");
				}
			}
		});
		socket.on("killNpc", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					int toId = data.getInt("to");
					npcs.remove(toId);
				} catch(JSONException e){
					DebugMessageFactory.printErrorMessage("ERROR UPDATING KILL NPCS");
				}
			}
		});
		socket.on("damageToPlayer", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					int fromId = data.getInt("from");
					String toId = data.getString("to");
					int amount = data.getInt("amount");

					if(playScreen != null) {
						if (toId.equals(clientID)) {
							playScreen.player.getDamage(fromId, amount);
						} else {
							playScreen.damageToOtherPlayer(toId, amount);
						}
					}
				} catch(JSONException e){
					DebugMessageFactory.printErrorMessage("ERROR AT DAMAGE TO PLAYER");
				}
			}
		});
		socket.on("itemDropped", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					int item_key = data.getInt("item_key");
					int x = data.getInt("x");
					int y = data.getInt("y");
					int amount = data.getInt("amount");
					String ownerId = data.getString("ownerId");

					if(playScreen != null){
						playScreen.addItem(item_key, x, y, amount, ownerId);
					}

				} catch(JSONException e){
					DebugMessageFactory.printErrorMessage("ERROR GETTING ITEM DROP");
				}
			}
		});
		socket.on("itemPicked", new Emitter.Listener() {
			@Override
			public void call(Object... args) {
				JSONObject data = (JSONObject) args[0];
				try {
					int item_key = data.getInt("item_key");
					int x = data.getInt("x");
					int y = data.getInt("y");

					if(playScreen != null){
						playScreen.removeItem(item_key, x, y);
					}

				} catch(JSONException e){
					DebugMessageFactory.printErrorMessage("ERROR GETTING ITEM DROP");
				}
			}
		});
	}

	@Override
	public void render () {
		super.render();
	}


	@Override
	public void pause() {
		super.pause();
		if(playScreen != null && playScreen.player != null) {
			playScreen.player.content.writeToFile();
		}
	}

	@Override
	public void dispose () {
		super.dispose();
	}
}
