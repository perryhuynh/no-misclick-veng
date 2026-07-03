/*
 * Copyright (c) 2026, Sun Clan
 * All rights reserved.
 *
 * This work is licensed under the terms of the
 * DO WHAT THE FUCK YOU WANT TO PUBLIC LICENSE, Version 2.
 * See the LICENSE file for the full license text.
 */
package com.nomisclickveng;

import net.runelite.api.gameval.InterfaceID;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ShouldBlockTest
{
	@Test
	public void blocksVengeanceClickWhileReboundActive()
	{
		assertTrue(NoMisclickVengPlugin.shouldBlock(InterfaceID.MagicSpellbook.VENGEANCE, 1));
	}

	@Test
	public void allowsVengeanceClickWhenReboundInactive()
	{
		assertFalse(NoMisclickVengPlugin.shouldBlock(InterfaceID.MagicSpellbook.VENGEANCE, 0));
	}

	@Test
	public void allowsOtherComponentsRegardlessOfRebound()
	{
		assertFalse(NoMisclickVengPlugin.shouldBlock(InterfaceID.MagicSpellbook.VENGEANCE_OTHER, 1));
		assertFalse(NoMisclickVengPlugin.shouldBlock(-1, 1));
	}
}
