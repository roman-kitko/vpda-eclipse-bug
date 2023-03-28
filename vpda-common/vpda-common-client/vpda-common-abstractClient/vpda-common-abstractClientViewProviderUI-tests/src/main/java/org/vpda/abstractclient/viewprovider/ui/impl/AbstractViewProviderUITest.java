/**
 * View provider driven applications - java application framework for developing RIA
 * Copyright (C) 2009-2022 Roman Kitko, Slovakia
 *
 * Licensed under the GNU GENERAL PUBLIC LICENSE, Version 3.0 (the "License").
 * You may not use this file except in compliance with the License.
 * You may obtain a copy of the License at http://www.gnu.org/licenses/gpl.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.vpda.abstractclient.viewprovider.ui.impl;

/**
 * @author kitko
 *
 */
public class AbstractViewProviderUITest {

//	/**
//	 * Test method for {@link org.vpda.abstractclient.viewprovider.ui.AbstractViewProviderUI#dispose()}.
//	 * @throws ViewProviderException 
//	 */
//	@Test public void testDispose() throws ViewProviderException {
//		createTestee().dispose();
//	}
//
//	/**
//	 * Test method for {@link org.vpda.abstractclient.viewprovider.ui.AbstractViewProviderUI#getFrame()}.
//	 * @throws ViewProviderException 
//	 */
//	@Test public void testGetFrame() throws ViewProviderException {
//		assertNotNull(createTestee().getFrame());
//	}
//
//	/**
//	 * Test method for {@link org.vpda.abstractclient.viewprovider.ui.AbstractViewProviderUI#getViewProviderID()}.
//	 * @throws ViewProviderException 
//	 */
//	@Test public void testGetViewProviderID() throws ViewProviderException {
//		assertNotNull(createTestee().getViewProviderID());
//	}
//
//	/**
//	 * Test method for {@link org.vpda.abstractclient.viewprovider.ui.AbstractViewProviderUI#getCallerProviderContext()}.
//	 * @throws ViewProviderException 
//	 */
//	@Test public void testGetViewProviderContext() throws ViewProviderException {
//		assertNull(createTestee().getCallerProviderContext());
//	}
//
//	/**
//	 * Test method for {@link org.vpda.abstractclient.viewprovider.ui.AbstractViewProviderUI#getViewProviderInfo()}.
//	 * @throws ViewProviderException 
//	 */
//	@Test public void testGetViewProviderInfo() throws ViewProviderException {
//		assertNotNull(createTestee().getViewProviderInfo());
//	}
//	
//	
//	@SuppressWarnings("unchecked")
//	private ViewProviderUI createTestee() throws ViewProviderException {
//		ViewProviderDef def = ViewProviderTestHelper.createTestViewProviderDef();
//		ResultWithCommands providerInfoResults = new ResultWithCommands<ViewProviderInitResponse>(new ViewProviderInitResponseImpl(new TestViewProviderInfo(def),new ListViewProviderSettingsImpl()),ViewProviderInitResponse.class);
//		ViewProvider viewProvider = EasyMock.createMock(ViewProvider.class);
//		EasyMock.expect(viewProvider.initProvider(EasyMock.isA(RequestWithCommands.class))).andReturn(providerInfoResults);
//		viewProvider.close();
//		EasyMock.replay(viewProvider);
//	
//		ClientServer clientServer = EasyMock.createMock(ClientServer.class);
//		try {
//			EasyMock.expect(clientServer.getUIProvider(EasyMock.isA(ViewProviderDef.class))).andReturn(viewProvider);
//		} catch (ViewProviderException e) {
//			fail(e.getMessage());
//		}
//		EasyMock.replay(clientServer);
//		WindowManager windowManager = EasyMock.createMock(WindowManager.class);
//		windowManager.registerAndShowFrame(EasyMock.isA(Frame.class));
//		EasyMock.expect(windowManager.generateFrameIdFromPrefix(EasyMock.isA(String.class))).andReturn("frame_0");
//		EasyMock.replay(windowManager);
//		
//		ViewProviderUI provider = InitializeeUtil.initInitializee(ViewProviderUI.class, new DummyViewProviderUI(ClientUtil.getClient(),clientServer,windowManager,def,null), createTestSession()) ;
//		return provider;
//		
//	}

}
