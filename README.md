# UltraCosmeticsPlugin
A very cool cosmetics plugin for Minecraft Hub Servers.

YOU CAN'T USE THE CODE FOR SOMETHING WHICH IS PAID. THE CODE CAN ONLY BE USED FOR A FREE PLUGIN AND IT MUST GIVE CREDIT TO ME THAT YOU USED MY CODE. THANKS

Normally, you should find out easily how to add a new cosmetic but I'll show an example with a Gadget that we'll 
name "Example".

Create the GadgetType EXAMPLE in the GadgetType Enum (Gadget.java)

Create the class GadgetExample, extend Gadget and create your gadget.

In the MessageManager, in the list where I add the name of all the gadgets add the one of your gadget like
addMessage("Gadgets.Example.name", "&7&lExample");

Then, register it in the onEnable method, where I register the gadgets by doing that:
gadgetList.add(new GadgetExample(null));

And last thing, go in the MenuListener in the method getGadgetByName, there is a switch loop, do that:
case EXAMPLE:
  new GadgetExample(player.getUniqueId());
  break;

And it's done! :)
