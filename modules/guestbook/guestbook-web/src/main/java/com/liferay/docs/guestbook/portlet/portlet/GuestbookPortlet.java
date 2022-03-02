package com.liferay.docs.guestbook.portlet.portlet;

import com.liferay.docs.guestbook.model.Guestbook;
import com.liferay.docs.guestbook.model.GuestbookEntry;
import com.liferay.docs.guestbook.portlet.constants.GuestbookPortletKeys;
import com.liferay.docs.guestbook.service.GuestbookEntryLocalService;
import com.liferay.docs.guestbook.service.GuestbookLocalService;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.portlet.bridges.mvc.MVCPortlet;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextFactory;
import com.liferay.portal.kernel.util.ParamUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

import javax.portlet.ActionRequest;
import javax.portlet.ActionResponse;
import javax.portlet.Portlet;
import javax.portlet.PortletException;
import javax.portlet.RenderRequest;
import javax.portlet.RenderResponse;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rhristov
 */
@Component(
		immediate = true,
		property = {
				"com.liferay.portlet.display-category=category.social",
				"javax.portlet.name=" + GuestbookPortletKeys.GUESTBOOK,
				"com.liferay.portlet.instanceable=false",
				"com.liferay.portlet.scopeable=true",
				"javax.portlet.display-name=Guestbook",
				"javax.portlet.expiration-cache=0",
				"javax.portlet.init-param.template-path=/",
				"javax.portlet.init-param.view-template=/guestbook/view.jsp",
				"javax.portlet.resource-bundle=content.Language",
				"javax.portlet.security-role-ref=power-user,user",
				"javax.portlet.supports.mime-type=text/html"
		},
		service = Portlet.class
)
public class GuestbookPortlet extends MVCPortlet {

	private static final Logger log = Logger.getLogger(GuestbookPortlet.class.getName());

	@Reference
	private GuestbookEntryLocalService guestbookEntryLocalService;

	@Reference
	private GuestbookLocalService guestbookLocalService;

	@Override
	public void render(RenderRequest renderRequest, RenderResponse renderResponse) throws IOException, PortletException {
		try {
			ServiceContext serviceContext = ServiceContextFactory.getInstance(Guestbook.class.getName(), renderRequest);
			long groupId = serviceContext.getScopeGroupId();
			long guestbookId = ParamUtil.getLong(renderRequest, "guestbookId");
			List<Guestbook> guestbooks = guestbookLocalService.getGuestbooks(groupId);
			if (guestbooks.isEmpty()) {
				Guestbook guestbook = guestbookLocalService.addGuestbook(serviceContext.getUserId(), "Main", serviceContext);
				guestbookId = guestbook.getGuestbookId();
			}
			if (guestbookId == 0) {
				guestbookId = guestbooks.get(0).getGuestbookId();
			}
			renderRequest.setAttribute("guestbookId", guestbookId);
		} catch (Exception e) {
			throw new PortletException(e);
		}
		super.render(renderRequest, renderResponse);
	}

	public void addEntry(ActionRequest request, ActionResponse response) throws PortalException {
		ServiceContext serviceContext = ServiceContextFactory.getInstance(GuestbookEntry.class.getName(), request);
		String userName = ParamUtil.getString(request, "name");
		String email = ParamUtil.getString(request, "email");
		String message = ParamUtil.getString(request, "message");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");
		long entryId = ParamUtil.getLong(request, "entryId");
		if (entryId > 0) {
			try {
				guestbookEntryLocalService.updateGuestbookEntry(
						serviceContext.getUserId(), guestbookId, entryId, userName, email, message, serviceContext);
				response.setRenderParameter("guestbookId", Long.toString(guestbookId));
			} catch (Exception e) {
				System.out.println(e);
				PortalUtil.copyRequestParameters(request, response);
				response.setRenderParameter("mvcPath", "/guestbook/edit_entry.jsp");
			}
		} else {
			try {
				guestbookEntryLocalService.addGuestbookEntry(serviceContext.getUserId(), guestbookId, userName,
						email, message, serviceContext);
				response.setRenderParameter("guestbookId", Long.toString(guestbookId));
			} catch (Exception e) {
				System.out.println(e);
				PortalUtil.copyRequestParameters(request, response);
				response.setRenderParameter("mvcPath", "/guestbook/edit_entry.jsp");
			}
		}
	}

	public void deleteEntry(ActionRequest request, ActionResponse response) throws PortalException {
		long entryId = ParamUtil.getLong(request, "entryId");
		long guestbookId = ParamUtil.getLong(request, "guestbookId");
		ServiceContext serviceContext = ServiceContextFactory.getInstance(GuestbookEntry.class.getName(), request);
		try {
			response.setRenderParameter("guestbookId", Long.toString(guestbookId));
			guestbookEntryLocalService.deleteGuestbookEntry(entryId);
		} catch (Exception e) {
			log.log(Level.SEVERE, "Exception occurred: ", e);
		}
	}
}