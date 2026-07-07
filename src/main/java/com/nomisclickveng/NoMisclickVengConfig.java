/*
 * Copyright (c) 2026, Perry
 * All rights reserved.
 *
 * This work is licensed under the terms of the BSD 2-Clause License.
 * See the LICENSE file for the full license text.
 */
package com.nomisclickveng;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("no-misclick-veng")
public interface NoMisclickVengConfig extends Config
{
	@ConfigItem(
		keyName = "showBlockedMessage",
		name = "Show blocked message",
		description = "Print a chat message when a Vengeance recast is blocked",
		position = 0
	)
	default boolean showBlockedMessage()
	{
		return true;
	}
}
