/*
 * Copyright (c) 2026, Perry
 * All rights reserved.
 *
 * This work is licensed under the terms of the BSD 2-Clause License.
 * See the LICENSE file for the full license text.
 */
package com.nomisclickveng;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class NoMisclickVengPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(NoMisclickVengPlugin.class);
		RuneLite.main(args);
	}
}
